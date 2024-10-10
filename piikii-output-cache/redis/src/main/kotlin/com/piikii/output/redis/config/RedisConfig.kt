package com.piikii.output.redis.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableRedisRepositories
@EnableCaching
@EnableConfigurationProperties(RedisProperties::class)
class RedisConfig {
    @Bean
    fun lettuceConnectionFactory(redisProperties: RedisProperties): LettuceConnectionFactory {
        val redisConfig =
            RedisStandaloneConfiguration().apply {
                hostName = redisProperties.host
                port = redisProperties.port
                password = RedisPassword.of(redisProperties.password)
            }

        val clientConfig =
            LettuceClientConfiguration.builder()
                .useSsl()
                .build()

        return LettuceConnectionFactory(redisConfig, clientConfig)
    }

    @Bean
    fun <T> redisTemplate(
        redisConnectionFactory: RedisConnectionFactory,
        objectMapper: ObjectMapper,
    ): RedisTemplate<String, T> {
        val redisTemplate = RedisTemplate<String, T>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer(objectMapper)
        return redisTemplate
    }

    @Bean
    fun cacheManager(redisConnectionFactory: RedisConnectionFactory): CacheManager {
        val redisCacheConfiguration =
            RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(7))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
                .serializeValuesWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(
                        GenericJackson2JsonRedisSerializer(),
                    ),
                )

        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(redisConnectionFactory)
            .cacheDefaults(redisCacheConfiguration)
            .build()
    }

    @Bean
    fun redissonClient(redisProperties: RedisProperties): RedissonClient? {
        val config = Config()
        val address = REDISSON_HOST_PREFIX + redisProperties.host + ":" + redisProperties.port
        config.useSingleServer()
            .setAddress(address)
            .setPassword(redisProperties.password)
            .setConnectionMinimumIdleSize(1)
        return Redisson.create(config)
    }

    companion object {
        const val REDISSON_HOST_PREFIX = "rediss://"
    }
}

@ConfigurationProperties(prefix = "redis")
data class RedisProperties(
    val host: String,
    val port: Int,
    val password: String,
)
