package com.piikii.input.http.aspect

import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.ServiceException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.expression.MethodBasedEvaluationContext
import org.springframework.core.DefaultParameterNameDiscoverer
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.stereotype.Component
import java.time.temporal.ChronoUnit
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * 중복요청 방지 제어 Annotation
 *
 * @property key key to determine duplicate requests (support SpEL)
 * @property timeUnit time unit of timeout
 * @property amount time amount of timeout
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PreventDuplicateAspectWithConcurrent(
    val key: String,
    val timeUnit: ChronoUnit = ChronoUnit.SECONDS,
    val amount: Long = 5,
)

@Aspect
@Component
class PreventDuplicateAspectWithConcurrentAspect {
    private val processingRequests = ConcurrentHashMap.newKeySet<String>()
    private val parser = SpelExpressionParser()
    private val parameterNameDiscoverer = DefaultParameterNameDiscoverer()
    private val executor = Executors.newVirtualThreadPerTaskExecutor()

    @Around("@annotation(PreventDuplicateRequest)")
    fun preventDuplicateRequest(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val annotation = method.getAnnotation(PreventDuplicateRequest::class.java)
        val key = generateKey(joinPoint, signature, annotation)

        if (!processingRequests.add(key)) {
            // if exists, throw Duplicated Request Exception
            throw ServiceException(ExceptionCode.DUPLICATED_REQUEST)
        }

        val future = executor.submit<Any> { joinPoint.proceed() }
        try {
            return future.get(annotation.amount, TimeUnit.of(annotation.timeUnit))
        } catch (e: TimeoutException) {
            future.cancel(true)
            throw ServiceException(ExceptionCode.REQUEST_TIMEOUT)
        } finally {
            processingRequests.remove(key)
        }
    }

    /**
     * 중복 요청으로 판단할 key 생성
     * - method name + SpEL parsed value
     *
     * @param joinPoint
     * @param signature
     * @param annotation
     * @return Key to determine duplicate requests
     */
    private fun generateKey(
        joinPoint: ProceedingJoinPoint,
        signature: MethodSignature,
        annotation: PreventDuplicateRequest,
    ): String {
        val method = signature.method
        val expression = parser.parseExpression(annotation.key)
        val context =
            MethodBasedEvaluationContext(
                joinPoint.target,
                method,
                joinPoint.args,
                parameterNameDiscoverer,
            )
        val parsedValue =
            expression.getValue(context, String::class.java)
                ?: throw ServiceException(
                    ExceptionCode.NOT_FOUNDED,
                    "중복요청 계산에 사용될 key가 없습니다 (method: ${method.name}, annotation key: ${annotation.key})",
                )
        return "${method.name}:$parsedValue"
    }
}
