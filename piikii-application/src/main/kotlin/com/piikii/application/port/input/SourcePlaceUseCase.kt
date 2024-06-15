package com.piikii.application.port.input

import com.piikii.application.domain.sourceplace.SourcePlace

interface SourcePlaceUseCase {
    fun save(): SourcePlace

    fun retrieve(): SourcePlace
}
