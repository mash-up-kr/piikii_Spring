package com.piikii.output.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RedisLockRepository(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    fun lock(key: String, timeout: Duration): Boolean {
        return redisTemplate.opsForValue()
            .setIfAbsent(key, LOCK, timeout) ?: false
    }

    fun lock(key: String): Boolean {
        return lock(
            key = key,
            timeout = DEFAULT_TIMEOUT
        )
    }

    fun unlock(key: String): Boolean {
        return redisTemplate.delete(key)
    }

    fun getCountRequestsInWindow(key: String): Long {
        return redisTemplate.opsForZSet().zCard(key)!!
    }

    fun removeOutOfWindow(key: String, epochSecond: Double, ttl: Duration) {
        val timeToLiveSeconds = ttl.seconds.toDouble()
        redisTemplate.opsForZSet().removeRangeByScore(key, MIN_TIMESTAMP, epochSecond - timeToLiveSeconds)
    }

    fun increaseRequestsInWindow(key: String, member: String, epochSecond: Double, ttl: Duration) {
        redisTemplate.opsForZSet().add(key, member, epochSecond)
        redisTemplate.expire(key, ttl)
    }

    companion object {
        const val LOCK = "lock"
        const val MIN_TIMESTAMP: Double = 0.0
        val DEFAULT_TIMEOUT: Duration = Duration.ofMinutes(5)
    }
}
