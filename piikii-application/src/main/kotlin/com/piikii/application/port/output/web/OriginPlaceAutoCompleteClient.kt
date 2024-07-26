package com.piikii.application.port.output.web

import com.piikii.application.domain.place.OriginPlace

interface OriginPlaceAutoCompleteClient {
    fun isAutoCompleteSupportedUrl(url: String): Boolean

    fun extractPlaceId(url: String): String

    fun getAutoCompletedPlace(
        url: String,
        placeId: String,
    ): OriginPlace
}
