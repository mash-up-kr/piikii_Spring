package com.piikii.application.domain.place

import com.piikii.application.port.input.OriginPlaceUseCase
import com.piikii.application.port.output.persistence.OriginPlaceCommandPort
import com.piikii.application.port.output.persistence.OriginPlaceQueryPort
import com.piikii.application.port.output.web.OriginPlaceAutoCompletePort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import org.springframework.stereotype.Service

@Service
class OriginPlaceService(
    private val originPlaceCommandPort: OriginPlaceCommandPort,
    private val originPlaceQueryPort: OriginPlaceQueryPort,
    private val originPlaceAutoCompletePorts: List<OriginPlaceAutoCompletePort>,
) : OriginPlaceUseCase {
    override fun getAutoCompleteOriginPlace(url: String): OriginPlace {
        val plainUrl = getUrlOfRemovedParameters(url)
        val originPlaceAutoCompleteClient =
            originPlaceAutoCompletePorts.firstOrNull { it.isAutoCompleteSupportedUrl(plainUrl) }
                ?: throw PiikiiException(
                    ExceptionCode.NOT_SUPPORT_AUTO_COMPLETE_URL,
                    "No AutoComplete client found for $url",
                )
        val originMapId = originPlaceAutoCompleteClient.extractOriginMapId(plainUrl)
        return originPlaceQueryPort.findByOriginMapId(originMapId)
            ?: originPlaceAutoCompleteClient.getAutoCompletedPlace(url = plainUrl, originMapId = originMapId)
                .let { originPlaceCommandPort.save(it) }
    }

    private fun getUrlOfRemovedParameters(url: String): String {
        return url.substringBefore("?")
    }
}
