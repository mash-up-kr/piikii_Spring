package com.piikii.output.web.avocado.adapter

import com.piikii.application.domain.place.OriginPlace
import com.piikii.application.port.output.web.PlaceAutoCompleteClient
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.web.avocado.parser.AvocadoPlaceIdParserStrategy
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class AvocadoPlaceAutoCompleteClient(
    private val avocadoPlaceIdParserStrategy: AvocadoPlaceIdParserStrategy,
    private val avocadoApiClient: RestClient,
) : PlaceAutoCompleteClient {
    override fun getAutoCompletedPlace(url: String): OriginPlace? {
        val plainUrl = url.substringBefore("?")

        val avocadoPlaceId =
            avocadoPlaceIdParserStrategy.parse(plainUrl)
                ?: throw PiikiiException(ExceptionCode.NOT_SUPPORT_AUTO_COMPLETE_URL)

        return avocadoApiClient.get()
            .uri("/$avocadoPlaceId")
            .retrieve()
            .body<AvocadoPlaceInfoResponse>()
            ?.toOriginPlace(plainUrl)
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.URL_PROCESS_ERROR,
                detailMessage = "origin: avocado, url : $plainUrl",
            )
    }
}
