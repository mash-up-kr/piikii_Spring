package com.piikii.application.port.input

import com.piikii.application.domain.place.SourcePlace

interface SourcePlaceUseCase {
    fun save(): SourcePlace

    fun retrieve(): SourcePlace
}
