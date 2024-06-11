package com.piikii.input.http.exception

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class ExceptionResponse(
    val message: String,
    val cause: String?,
    val timestamp: Long
)
