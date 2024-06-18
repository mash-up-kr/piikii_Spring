package com.piikii.common.client

import org.springframework.web.client.RestClient

interface PiikiiRestClient {
    fun build(): RestClient
}
