package com.piikii.output.web.lemon.adapter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.piikii.application.domain.place.OriginPlace
import com.piikii.application.port.output.web.PlaceAutoCompleteClient
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.web.lemon.salt.LemonSaltAdditive
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class LemonPlaceAutoCompleteClient(
    private val lemonSaltAdditive: LemonSaltAdditive,
    private val objectMapper: ObjectMapper,
) : PlaceAutoCompleteClient {
    override fun getAutoCompletedPlace(url: String): OriginPlace? {
        val saltedUrl = lemonSaltAdditive.execute(url)
        val response = fetchResponse(saltedUrl)
        val placeResponse = parseResponse(response)
        return placeResponse.toOriginPlace(url)
    }

    private fun fetchResponse(url: String): String {
        val client = RestClient.builder().baseUrl(url).build()
        return client.get().retrieve().body(String::class.java) ?: throw PiikiiException(
            exceptionCode = ExceptionCode.URL_PROCESS_ERROR,
            detailMessage = "origin: lemon, url : $url",
        )
    }

    private fun parseResponse(response: String): LemonPlaceInfoResponse {
        return objectMapper.readValue(response)
    }
}
