package com.piikii.application.domain.course

data class Distance(
    val totalDistanceMeter: Int? = null,
    val totalTimeMinute: Int? = null,
) {
    companion object {
        val EMPTY = Distance(null, null)
    }
}
