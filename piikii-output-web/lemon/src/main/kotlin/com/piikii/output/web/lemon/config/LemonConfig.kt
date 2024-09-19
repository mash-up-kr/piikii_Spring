package com.piikii.output.web.lemon.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestClient

@Configuration
@EnableConfigurationProperties(LemonProperties::class, LemonCoordinateProperties::class)
open class LemonConfig {
    @Bean
    open fun lemonApiClient(lemonProperties: LemonProperties): RestClient {
        return RestClient.builder()
            .baseUrl(lemonProperties.url.api)
            .build()
    }

    @Bean
    open fun lemonCoordinateApiClient(lemonCoordinateProperties: LemonCoordinateProperties): RestClient {
        return RestClient.builder()
            .defaultHeader(HttpHeaders.AUTHORIZATION, lemonCoordinateProperties.auth)
            .baseUrl(lemonCoordinateProperties.url)
            .build()
    }
}

@ConfigurationProperties(prefix = "lemon")
data class LemonProperties(
    val url: LemonUrl,
)

data class LemonUrl(
    val regex: Regex,
    val api: String,
) {
    data class Regex(
        val web: String,
        val mobileWeb: String,
        val mobileApp: String,
    )
}

@ConfigurationProperties(prefix = "coordinate-api")
data class LemonCoordinateProperties(
    val url: String,
    val auth: String,
)
