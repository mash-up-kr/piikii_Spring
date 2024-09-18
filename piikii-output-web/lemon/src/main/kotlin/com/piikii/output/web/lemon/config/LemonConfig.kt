package com.piikii.output.web.lemon.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
@EnableConfigurationProperties(LemonProperties::class)
class LemonConfig {
    @Bean
    fun lemonApiClient(lemonProperties: LemonProperties): RestClient {
        return RestClient.builder()
            .baseUrl(lemonProperties.url.api)
            .build()
    }

    @Bean
    fun lemonCoordinateApiClient(lemonProperties: LemonProperties): RestClient {
        return RestClient.builder()
            .defaultHeader("Authorization", lemonProperties.apiKey)
            .baseUrl(lemonProperties.coordinateApiUrl)
            .build()
    }
}

@ConfigurationProperties(prefix = "lemon")
data class LemonProperties(
    val url: LemonUrl,
    val coordinateApiUrl: String,
    val apiKey: String,
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
