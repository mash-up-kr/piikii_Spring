package com.piikii.input.http.dto

open class ResponseForm<T>(
    val data: T? = null,
    val message: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
) {
    companion object {
        val EMPTY_RESPONSE = ResponseForm<Unit>()
    }
}
