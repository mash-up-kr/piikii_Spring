package com.piikii.input.http.controller.docs

import com.piikii.application.port.input.dto.request.PlaceAutoCompleteUrlRequest
import com.piikii.application.port.input.dto.response.PlaceAutoCompleteResponse
import com.piikii.input.http.controller.dto.ResponseForm
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

class SuccessAutoCompleteByUrl : ResponseForm<PlaceAutoCompleteResponse>()

@Tag(name = "OriginPlaceApi", description = "OriginPlace Api 입니다.")
interface OriginPlaceApiDocs {
    @Operation(summary = "장소입력 자동완성 API", description = "AVOCADO/LEMON MAP URL을 통한 장소입력 자동완성 API")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "자동완성을 위한 장소정보 반환 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SuccessAutoCompleteByUrl::class),
                    ),
                ],
            ),
        ],
    )
    fun placeAutoCompleteByUrl(request: PlaceAutoCompleteUrlRequest): ResponseForm<PlaceAutoCompleteResponse>
}
