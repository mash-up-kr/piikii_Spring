package com.piikii.input.http.controller.dto

open class ResponseForm<T>(
    val data: T? = null,
    val timestamp: Long = System.currentTimeMillis(),
) {
    companion object {
        val EMPTY_RESPONSE = ResponseForm<Unit>()
    }
}
