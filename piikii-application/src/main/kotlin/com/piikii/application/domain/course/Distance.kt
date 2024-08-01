package com.piikii.application.domain.course

data class Distance(
    val totalDistanceMeter: Int?,
    val totalTimeMinute: Int?,
) {
    companion object {
        fun empty() = Distance(null, null)
    }
}
