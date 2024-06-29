package com.piikii.input.http.controller.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class ExceptionResponse(
    val message: String?,
    val cause: String?,
    val timestamp: Long,
)
