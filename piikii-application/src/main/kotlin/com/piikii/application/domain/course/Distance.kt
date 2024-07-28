package com.piikii.application.domain.course

data class Distance(
    val totalDistance: Int?,
    val totalTime: Int?,
) {
    companion object {
        fun empty() = Distance(null, null)
    }
}
