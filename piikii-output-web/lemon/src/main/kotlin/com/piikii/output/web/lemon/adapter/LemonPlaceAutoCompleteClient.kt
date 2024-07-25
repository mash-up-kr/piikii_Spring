package com.piikii.output.web.lemon.adapter

import com.piikii.application.domain.place.OriginPlace
import com.piikii.application.port.output.web.PlaceAutoCompleteClient
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
) : PlaceAutoCompleteClient {
    override fun getAutoCompletedPlace(url: String): OriginPlace? {
        // TODO: 앞단에서 URL preprocessing 고려
        val plainUrl = url.substringBefore("?")
        val lemonPlaceId =
            lemonPlaceIdParser.parse(plainUrl)
                ?: throw PiikiiException(ExceptionCode.NOT_SUPPORT_AUTO_COMPLETE_URL)

        return lemonApiClient.get()
            .uri("/$lemonPlaceId")
            .retrieve()
            .body<LemonPlaceInfoResponse>()
            ?.toOriginPlace(plainUrl)
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.URL_PROCESS_ERROR,
                detailMessage = "origin: lemon, url : $plainUrl",
            )
    }
}
