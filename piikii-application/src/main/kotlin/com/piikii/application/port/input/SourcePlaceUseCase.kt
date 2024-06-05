package com.piikii.application.port.input

import com.piikii.application.domain.model.sourceplace.SourcePlace

interface SourcePlaceUseCase {
    fun save(): SourcePlace
}
