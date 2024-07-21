package com.piikii.output.web.lemon.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(LemonProperties::class)
class LemonConfig

@ConfigurationProperties(prefix = "lemon")
data class LemonProperties(
    val baseUrl: String,
    val salt: String,
)
