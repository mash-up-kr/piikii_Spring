package com.piikii.application.domain.course

data class Coordinate(
    val x: Double?,
    val y: Double?,
) {
    fun isValid(): Boolean {
        return x != null && y != null
    }
}
