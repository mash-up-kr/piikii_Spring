package com.piikii.input.http.dto

class ResponseForm<T>(
    val data: T? = null,
    val message: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
)
