package com.piikii.output.web.lemon.adapter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.piikii.application.domain.place.OriginPlace
import com.piikii.application.port.output.web.UrlAccessor
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.web.lemon.UrlAccessorResponse
import com.piikii.output.web.lemon.util.SaltAdditive
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class LemonUrlAccessor(
    private val saltAdditive: SaltAdditive,
    private val objectMapper: ObjectMapper,
) : UrlAccessor {
    override fun get(url: String): OriginPlace {
        val saltedUrl = saltAdditive.execute(url)
        val response = fetchResponse(saltedUrl)
        val placeResponse = parseResponse(response)
        return placeResponse.toOriginPlace(url)
    }

    private fun fetchResponse(url: String): String {
        val client = RestClient.builder().baseUrl(url).build()
        return client.get().retrieve().body(String::class.java) ?: throw PiikiiException(
            exceptionCode = ExceptionCode.URL_PROCESS_ERROR,
            detailMessage = "url : $url",
        )
    }

    private fun parseResponse(response: String): UrlAccessorResponse {
        return objectMapper.readValue(response)
    }
}
