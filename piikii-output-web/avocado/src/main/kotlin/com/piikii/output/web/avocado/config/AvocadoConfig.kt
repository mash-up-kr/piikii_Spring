package com.piikii.output.web.avocado.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestClient

@Configuration
@EnableConfigurationProperties(AvocadoProperties::class)
class AvocadoConfig {
    @Bean
    fun avocadoApiClient(avocadoProperties: AvocadoProperties): RestClient {
        val httpProperties = avocadoProperties.http
        return RestClient.builder()
            .baseUrl(avocadoProperties.url.api)
            .defaultHeaders {
                it.add(HttpHeaders.COOKIE, httpProperties.cookies)
                it.add(HttpHeaders.USER_AGENT, httpProperties.userAgent)
                it.add(HttpHeaders.ACCEPT_LANGUAGE, "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4")
                it.add(HttpHeaders.CACHE_CONTROL, "no-cache")
                it.add(HttpHeaders.PRAGMA, "no-cache")

                it.add("Priority", "u=1")
                it.add("Dnt", "1")

                it.add("Sec-Ch-Ua", "\"Not-A.Brand\";v=\"99\", \"Chromium\";v=\"124\"")
                it.add("Sec-Ch-Ua-Mobile", "?0")
                it.add("Sec-Ch-Ua-Platform", "MacOS")

                it.add("Sec-Fetch-Dest", "empty")
                it.add("Sec-Fetch-Mode", "cors")
                it.add("Sec-Fetch-Site", "same-origin")
            }
            .build()
    }
}

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
