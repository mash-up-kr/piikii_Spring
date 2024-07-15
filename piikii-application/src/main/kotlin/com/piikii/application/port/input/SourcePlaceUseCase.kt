package com.piikii.application.port.input

import com.piikii.application.domain.place.OriginPlace

interface SourcePlaceUseCase {
    fun save(): OriginPlace

    fun retrieve(): OriginPlace
}
