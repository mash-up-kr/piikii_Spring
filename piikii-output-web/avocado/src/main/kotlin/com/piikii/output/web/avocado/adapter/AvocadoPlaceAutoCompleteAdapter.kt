package com.piikii.output.web.avocado.adapter

import com.piikii.application.domain.place.OriginMapId
import com.piikii.application.domain.place.OriginPlace
import com.piikii.application.port.output.web.OriginPlaceAutoCompletePort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.web.avocado.config.AvocadoProperties
import com.piikii.output.web.avocado.parser.AvocadoOriginMapIdParserStrategy
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Component
class AvocadoPlaceAutoCompleteAdapter(
    private val avocadoOriginMapIdParserStrategy: AvocadoOriginMapIdParserStrategy,
    private val avocadoApiClient: RestClient,
    private val avocadoProperties: AvocadoProperties,
) : OriginPlaceAutoCompletePort {
    override fun isAutoCompleteSupportedUrl(url: String): Boolean {
        return avocadoOriginMapIdParserStrategy.getParserBySupportedUrl(url) != null
    }

    override fun extractOriginMapId(url: String): OriginMapId {
        return avocadoOriginMapIdParserStrategy.getParserBySupportedUrl(url)?.parseOriginMapId(url)
            ?: throw PiikiiException(ExceptionCode.NOT_SUPPORT_AUTO_COMPLETE_URL)
    }

    override fun getAutoCompletedPlace(
        url: String,
        originMapId: OriginMapId,
    ): OriginPlace {
        val id = originMapId.toId()
        return avocadoApiClient.get()
            .uri("/$id")
            .header(HttpHeaders.REFERER, "${avocadoProperties.url.referer}$id")
            .retrieve()
            .body<AvocadoPlaceInfoResponse>()
            ?.toOriginPlace(url)
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.URL_PROCESS_ERROR,
                detailMessage = "origin: avocado, url : $url",
            )
    }
}
