package com.piikii.input.http.controller

import com.piikii.application.port.input.OriginPlaceUseCase
import com.piikii.application.port.input.dto.request.PlaceAutoCompleteUrlRequest
import com.piikii.application.port.input.dto.response.PlaceAutoCompleteResponse
import com.piikii.input.http.controller.docs.OriginPlaceApiDocs
import com.piikii.input.http.controller.dto.ResponseForm
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/v1/place/auto-complete")
class OriginPlaceApi(
    private val originPlaceUseCase: OriginPlaceUseCase,
) : OriginPlaceApiDocs {
    @PostMapping("/url")
    override fun placeAutoCompleteByUrl(
        @RequestBody request: PlaceAutoCompleteUrlRequest,
    ): ResponseForm<PlaceAutoCompleteResponse> {
        val originPlace = originPlaceUseCase.getAutoCompleteOriginPlace(request.url)
        return ResponseForm(PlaceAutoCompleteResponse.from(originPlace))
    }
}
