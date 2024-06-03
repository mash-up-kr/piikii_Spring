package com.piikii.application.port.input

import com.piikii.application.domain.model.SourcePlace

interface SourcePlaceUseCase {
    fun save(): SourcePlace
}
