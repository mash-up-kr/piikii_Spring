package com.piikii.output.web.lemon.adapter

import com.piikii.application.domain.place.OriginPlace
import com.piikii.application.port.output.web.OriginPlaceAutoCompleteClient
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.web.lemon.parser.LemonPlaceIdParser
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class LemonPlaceAutoCompleteClient(
    private val lemonPlaceIdParser: LemonPlaceIdParser,
    private val lemonApiClient: RestClient,
) : OriginPlaceAutoCompleteClient {
    override fun isAutoCompleteSupportedUrl(url: String): Boolean {
        return lemonPlaceIdParser.isAutoCompleteSupportedUrl(url)
    }

    override fun extractPlaceId(url: String): String {
        return lemonPlaceIdParser.parse(url)
            ?: throw PiikiiException(ExceptionCode.NOT_SUPPORT_AUTO_COMPLETE_URL)
    }

    override fun getAutoCompletedPlace(
        url: String,
        placeId: String,
    ): OriginPlace {
        return lemonApiClient.get()
            .uri("/$placeId")
            .retrieve()
            .body<LemonPlaceInfoResponse>()
            ?.toOriginPlace(url)
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.URL_PROCESS_ERROR,
                detailMessage = "origin: lemon, url : $url",
            )
    }
}
