package com.piikii.input.http.controller

import com.piikii.application.domain.place.OriginPlace
import com.piikii.application.port.input.OriginPlaceUseCase
import com.piikii.input.http.controller.docs.OriginPlaceApiDocs
import org.springframework.web.bind.annotation.RestController

@RestController
class OriginPlaceApi(
    private val originPlaceUseCase: OriginPlaceUseCase,
) : OriginPlaceApiDocs {
    override fun testPost(): OriginPlace {
        return originPlaceUseCase.save()
    }

    override fun testGet(): OriginPlace {
        return originPlaceUseCase.retrieve()
    }
}
