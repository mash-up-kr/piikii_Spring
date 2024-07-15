package com.piikii.input.http.controller.docs

import com.piikii.application.domain.place.OriginPlace
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "SourcePlaceApi", description = "SourcePlace Api 입니다.")
interface SourcePlaceApiDocs {
    @Operation(summary = "create test API", description = "sample 생성 요청 메서드")
    @ApiResponses(value = [ApiResponse(responseCode = "201", description = "CREATED test")])
    fun testPost(): OriginPlace

    @Operation(summary = "get test API", description = "sample 조회 요청 메서드")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "GET test")])
    fun testGet(): OriginPlace
}
