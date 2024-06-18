package com.piikii.input.http.client

import com.piikii.common.client.PiikiiRestClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class PiikiiSlackRestClient(
    @Value("\${webhook.slack.url}")
    private val slackHookURL: String,
) : PiikiiRestClient {
    override fun build(): RestClient {
        return RestClient.builder().baseUrl(slackHookURL).build()
    }
}
