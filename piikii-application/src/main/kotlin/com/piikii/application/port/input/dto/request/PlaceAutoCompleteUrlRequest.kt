package com.piikii.application.port.input.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

data class PlaceAutoCompleteUrlRequest(
    @field:NotNull(message = "URL 입력은 필수 입니다.")
    @field:Schema(description = "장소 자동완성을 위한 지도 URL", example = "https://test.com/1231421")
    val url: String,
)
