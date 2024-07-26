package com.piikii.output.web.avocado.adapter

import com.piikii.application.domain.place.OriginMapId
import com.piikii.application.domain.place.OriginPlace
import com.piikii.application.port.output.web.OriginPlaceAutoCompleteClient
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
) : OriginPlaceAutoCompleteClient {
    override fun isAutoCompleteSupportedUrl(url: String): Boolean {
        return avocadoPlaceIdParserStrategy.getParserBySupportedUrl(url) != null
    }

    override fun extractOriginMapId(url: String): OriginMapId {
        return avocadoPlaceIdParserStrategy.getParserBySupportedUrl(url)?.parseOriginMapId(url)
            ?: throw PiikiiException(ExceptionCode.NOT_SUPPORT_AUTO_COMPLETE_URL)
    }

    override fun getAutoCompletedPlace(
        url: String,
        originMapId: OriginMapId,
    ): OriginPlace {
        return avocadoApiClient.get()
            .uri("/${originMapId.value}")
            .retrieve()
            .body<AvocadoPlaceInfoResponse>()
            ?.toOriginPlace(url)
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.URL_PROCESS_ERROR,
                detailMessage = "origin: avocado, url : $url",
            )
    }
}
