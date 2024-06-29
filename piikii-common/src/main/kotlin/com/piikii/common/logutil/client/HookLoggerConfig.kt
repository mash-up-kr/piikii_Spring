package com.piikii.common.logutil.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class HookLoggerConfig(
    @Value("\${webhook.slack.url}")
    private val url: String,
) {
    @Bean
    fun getSlackHookLoggerInstance(): RestClient {
        return RestClient.builder().baseUrl(url).build()
    }
}
