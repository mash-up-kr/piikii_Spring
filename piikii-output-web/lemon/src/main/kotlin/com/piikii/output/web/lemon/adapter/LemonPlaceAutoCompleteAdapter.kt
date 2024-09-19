package com.piikii.output.web.lemon.adapter

import com.piikii.application.domain.place.OriginMapId
import com.piikii.application.domain.place.OriginPlace
import com.piikii.application.port.output.web.OriginPlaceAutoCompletePort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.web.lemon.parser.LemonOriginMapIdParserStrategy
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class LemonPlaceAutoCompleteAdapter(
    private val lemonOriginMapIdParserStrategy: LemonOriginMapIdParserStrategy,
    private val lemonApiClient: RestClient,
    private val lemonCoordinateApiClient: RestClient,
) : OriginPlaceAutoCompletePort {
    override fun isAutoCompleteSupportedUrl(url: String): Boolean =
        lemonOriginMapIdParserStrategy.getParserBySupportedUrl(url) != null

    override fun extractOriginMapId(url: String): OriginMapId =
        lemonOriginMapIdParserStrategy.getParserBySupportedUrl(url)?.parseOriginMapId(url)
            ?: throw PiikiiException(ExceptionCode.NOT_SUPPORT_AUTO_COMPLETE_URL)

    override fun getAutoCompletedPlace(
        url: String,
        originMapId: OriginMapId,
    ): OriginPlace {
        val originPlace =
            lemonApiClient.get()
                .uri("/${originMapId.toId()}")
                .retrieve()
                .body<LemonPlaceInfoResponse>()
                ?.toOriginPlace(url)
                ?: throw PiikiiException(
                    exceptionCode = ExceptionCode.URL_PROCESS_ERROR,
                    detailMessage = "origin: lemon, url: $url",
                )

        val lemonCoordinateResponse =
            lemonCoordinateApiClient.get()
                .uri { it.queryParam("query", originPlace.address).build() }
                .retrieve()
                .body<LemonCoordinateResponse>()

        lemonCoordinateResponse?.documents?.let { document ->
            originPlace.longitude = document.firstOrNull()?.x?.toDouble()
            originPlace.latitude = document.firstOrNull()?.y?.toDouble()
        }

        return originPlace
    }
}
