package com.piikii.output.redis

import java.time.Duration
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisLockRepository(
    private val redisTemplate: RedisTemplate<String, String>
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

    companion object {
        const val LOCK = "lock"
        val DEFAULT_TIMEOUT: Duration = Duration.ofMinutes(5)
    }
}
