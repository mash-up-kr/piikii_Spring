package com.piikii.output.web.lemon.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "lemon")
data class LemonProperties(
    val baseUrl: String,
    val salt: String,
)
