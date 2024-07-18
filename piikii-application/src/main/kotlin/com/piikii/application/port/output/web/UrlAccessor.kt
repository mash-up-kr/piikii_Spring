package com.piikii.application.port.output.web

import com.piikii.application.domain.place.OriginPlace

interface UrlAccessor {
    fun get(): OriginPlace
}
