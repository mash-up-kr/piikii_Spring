package com.piikii.input.http.controller

import com.piikii.application.domain.model.sourceplace.SourcePlace
import com.piikii.application.port.input.SourcePlaceUseCase
import com.piikii.input.http.docs.SourcePlaceApiDocs
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SourcePlaceApi(
    private val sourcePlaceUseCase: SourcePlaceUseCase
) : SourcePlaceApiDocs {

    @PostMapping("/test")
    override fun testPost(): SourcePlace {
        return sourcePlaceUseCase.save()
    }

    @GetMapping("/test")
    override fun testGet(): SourcePlace {
        return sourcePlaceUseCase.retrieve()
    }

}
