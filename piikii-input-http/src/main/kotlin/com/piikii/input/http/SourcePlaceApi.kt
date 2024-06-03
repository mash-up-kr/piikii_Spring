package com.piikii.input.http

import com.piikii.application.domain.model.SourcePlace
import com.piikii.application.port.input.SourcePlaceUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SourcePlaceApi(
    private val sourcePlaceUseCase: SourcePlaceUseCase
) {

    @GetMapping("/test")
    fun test(): SourcePlace {
        return sourcePlaceUseCase.save()
    }
}
