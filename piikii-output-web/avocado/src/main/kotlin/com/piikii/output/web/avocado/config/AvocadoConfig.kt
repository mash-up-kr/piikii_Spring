package com.piikii.output.web.avocado.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(AvocadoProperties::class)
class AvocadoConfig

@ConfigurationProperties(prefix = "avocado")
data class AvocadoProperties(
    val http: AvocadoHttp,
    val url: AvocadoUrl,
)

data class AvocadoHttp(
    val cookies: String,
    val userAgent: String,
)

data class AvocadoUrl(
    val regex: Regex,
    val api: String,
) {
    data class Regex(
        val web: String,
        val share: String,
    )

    fun webRegex(): kotlin.text.Regex {
        return regex.web.toRegex()
    }

    fun shareRegex(): kotlin.text.Regex {
        return regex.share.toRegex()
    }
}
