package com.piikii.input.http.aspect

import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.redis.RedisLockRepository
import java.time.Duration
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.expression.MethodBasedEvaluationContext
import org.springframework.core.DefaultParameterNameDiscoverer
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * 중복요청 방지 제어 Annotation
 *
 * @property key key to determine duplicate requests (support SpEL)
 * @property timeoutMillis default 7 seconds
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PreventDuplicateRequest(
    val key: String,
    val timeoutMillis: Long = 5_000,
)

@Aspect
@Component
class PreventDuplicateAspect(
    private val redisLockRepository: RedisLockRepository,
) {
    private val parser = SpelExpressionParser()
    private val parameterNameDiscoverer = DefaultParameterNameDiscoverer()

    @Around("@annotation(PreventDuplicateRequest)")
    fun preventDuplicateRequest(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val annotation = method.getAnnotation(PreventDuplicateRequest::class.java)
        val key = generateKey(joinPoint, signature, annotation)

        if (!redisLockRepository.lock(key, Duration.ofMillis(annotation.timeoutMillis))) {
            // if exists, throw Duplicated Request Exception
            throw PiikiiException(ExceptionCode.DUPLICATED_REQUEST)
        }

        try {
            return joinPoint.proceed()
        } finally {
            redisLockRepository.unlock(key)
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
                ?: throw PiikiiException(
                    ExceptionCode.NOT_FOUNDED,
                    "중복요청 계산에 사용될 key가 없습니다 (method: ${method.name}, annotation key: ${annotation.key})",
                )
        return "${method.name}_$parsedValue"
    }
}
