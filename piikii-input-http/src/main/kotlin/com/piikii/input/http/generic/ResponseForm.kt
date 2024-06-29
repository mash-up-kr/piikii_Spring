package com.piikii.input.http.generic

class ResponseForm<T>(
    val data: T? = null,
    val message: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
)
