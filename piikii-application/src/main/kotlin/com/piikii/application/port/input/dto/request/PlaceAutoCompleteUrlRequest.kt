package com.piikii.application.port.input.dto.request

import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.net.URI

data class PlaceAutoCompleteUrlRequest(
    @field:NotBlank(message = "URL 입력은 필수 입니다.")
    @field:Schema(description = "장소 자동완성을 위한 지도 URL", example = "https://test.com/1231421")
    var url: String,
) {
    init {
        val validatedUrl =
            Regex(REGEX_PATTERN).find(url)?.value ?: throw PiikiiException(
                exceptionCode = ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION,
                detailMessage = "URL이 요청 형식에 맞지 않습니다. : $url",
            )
        url = URI(validatedUrl).toString()
    }

    companion object {
        private const val REGEX_PATTERN = "(https://\\S+)"
    }
}
