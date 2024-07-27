package com.piikii.output.web.lemon.adapter

import com.piikii.application.domain.place.OriginMapId
import com.piikii.application.domain.place.OriginPlace
import com.piikii.application.port.output.web.OriginPlaceAutoCompleteClient
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.web.lemon.parser.LemonOriginMapIdParser
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class LemonPlaceAutoCompleteClient(
    private val lemonOriginMapIdParser: LemonOriginMapIdParser,
    private val lemonApiClient: RestClient,
) : OriginPlaceAutoCompleteClient {
    override fun isAutoCompleteSupportedUrl(url: String): Boolean {
        return lemonOriginMapIdParser.isAutoCompleteSupportedUrl(url)
    }

    override fun extractOriginMapId(url: String): OriginMapId {
        return lemonOriginMapIdParser.parseOriginMapId(url)
            ?: throw PiikiiException(ExceptionCode.NOT_SUPPORT_AUTO_COMPLETE_URL)
    }

    override fun getAutoCompletedPlace(
        url: String,
        originMapId: OriginMapId,
    ): OriginPlace {
        return lemonApiClient.get()
            .uri("/${originMapId.toId()}")
            .retrieve()
            .body<LemonPlaceInfoResponse>()
            ?.toOriginPlace(url)
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.URL_PROCESS_ERROR,
                detailMessage = "origin: lemon, url : $url",
            )
    }
}
