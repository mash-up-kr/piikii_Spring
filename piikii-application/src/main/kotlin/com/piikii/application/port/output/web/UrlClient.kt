package com.piikii.application.port.output.web

import com.piikii.application.domain.place.OriginPlace

interface UrlClient {
    fun get(url: String): OriginPlace
}
