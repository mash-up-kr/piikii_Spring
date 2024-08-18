package com.piikii.application.port.output.web

import com.piikii.application.domain.place.OriginMapId
import com.piikii.application.domain.place.OriginPlace

interface OriginPlaceAutoCompletePort {
    fun isAutoCompleteSupportedUrl(url: String): Boolean

    fun extractOriginMapId(url: String): OriginMapId

    fun getAutoCompletedPlace(
        url: String,
        originMapId: OriginMapId,
    ): OriginPlace
}
