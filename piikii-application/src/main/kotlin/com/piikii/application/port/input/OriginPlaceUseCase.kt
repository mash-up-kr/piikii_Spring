package com.piikii.application.port.input

import com.piikii.application.domain.place.OriginPlace

interface OriginPlaceUseCase {
    fun save(): OriginPlace

    fun retrieve(): OriginPlace
}
