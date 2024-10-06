package com.piikii.input.http.aspect

import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.ServiceException
import com.piikii.output.redis.RedisLockRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.expression.MethodBasedEvaluationContext
import org.springframework.core.DefaultParameterNameDiscoverer
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class LimitConcurrentRequest(
    val key: String,
    val maxRequestCount: Long = 1,
    val timeUnit: ChronoUnit = ChronoUnit.SECONDS,
    val amount: Long = 5,
)

@Aspect
@Component
class LimitConcurrentRequestAspect(
    private val redisLockRepository: RedisLockRepository,
) {
    private val parser = SpelExpressionParser()
    private val parameterNameDiscoverer = DefaultParameterNameDiscoverer()

    @Around("@annotation(RateLimiter)")
    fun limitConcurrentRequest(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val annotation = method.getAnnotation(LimitConcurrentRequest::class.java)

        val key = "req_concurrent_limit:${method.name}"
        val epochSecond = Instant.now().epochSecond.toDouble()

        val member = generateMember(
            memberSuffix = epochSecond.toString(),
            joinPoint = joinPoint,
            signature = signature,
            annotation = annotation
        )

        val ttl = Duration.of(annotation.amount, annotation.timeUnit)
        redisLockRepository.removeOutOfWindow(key, epochSecond, ttl)
        if (redisLockRepository.getCountRequestsInWindow(key) >= annotation.maxRequestCount) {
            throw ServiceException(ExceptionCode.REQUEST_LIMIT_EXCEEDED_IN_TIME_WINDOW)
        }

        redisLockRepository.increaseRequestsInWindow(key, member, epochSecond, ttl)
        return joinPoint.proceed()
    }

    /**
     * 요청 인식용 Member parsing
     * - using SpEL parsed value
     */
    private fun generateMember(
        memberSuffix: String,
        joinPoint: ProceedingJoinPoint,
        signature: MethodSignature,
        annotation: LimitConcurrentRequest,
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
        val member = expression.getValue(context, String::class.java)
            ?: throw ServiceException(
                ExceptionCode.NOT_FOUNDED,
                "LimitConcurrentRequest member 추출에 실패했습니다 (method: ${method.name}, annotation key: ${annotation.key})",
            )
        return "$member:$memberSuffix"
    }

}
