package com.piikii.output.web.tmap.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
@EnableConfigurationProperties(TmapProperties::class)
class TmapConfig {
    @Bean
    fun tmapApiClient(tmapProperties: TmapProperties): RestClient {
        return RestClient.builder()
            .baseUrl(tmapProperties.url.api)
            .defaultHeaders {
                it.add("Accept", "application/json")
                it.add("Content-Type", "application/json")
                it.add("appKey", tmapProperties.key.app)
            }
            .build()
    }
}

@ConfigurationProperties(prefix = "tmap")
data class TmapProperties(
    val url: TmapUrl,
    val key: TmapKey,
)

data class TmapUrl(
    val api: String,
)

data class TmapKey(
    val app: String,
)
