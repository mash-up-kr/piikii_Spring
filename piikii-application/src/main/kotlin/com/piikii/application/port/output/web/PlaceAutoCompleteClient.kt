package com.piikii.application.port.output.web

import com.piikii.application.domain.place.OriginPlace

interface PlaceAutoCompleteClient {
    fun getAutoCompletedPlace(url: String): OriginPlace?
}
