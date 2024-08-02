package com.piikii.application.domain.course

data class Distance(
    val totalDistanceMeter: Int?,
    val totalTimeMinute: Int?,
) {
    companion object {
        val EMPTY = Distance(null, null)
    }
}
