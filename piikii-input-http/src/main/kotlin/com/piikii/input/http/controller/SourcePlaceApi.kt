package com.piikii.input.http.controller

import com.piikii.application.domain.sourceplace.SourcePlace
import com.piikii.application.port.input.SourcePlaceUseCase
import com.piikii.input.http.docs.SourcePlaceApiDocs
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SourcePlaceApi(
    private val sourcePlaceUseCase: SourcePlaceUseCase
) : SourcePlaceApiDocs {

    override fun testPost(): SourcePlace {
        return sourcePlaceUseCase.save()
    }

    override fun testGet(): SourcePlace {
        return sourcePlaceUseCase.retrieve()
    }

}
