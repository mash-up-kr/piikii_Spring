package com.piikii.input.http.generic

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class ResponseForm<T>(
    val data: T? = null,
    val message: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
)
