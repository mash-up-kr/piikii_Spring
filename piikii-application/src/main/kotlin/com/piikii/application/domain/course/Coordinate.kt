package com.piikii.application.domain.course

import com.piikii.application.domain.place.OriginPlace

data class Coordinate(
    val x: Double?,
    val y: Double?,
) {
    fun isValid(): Boolean {
        return x != null && y != null
    }

    companion object {
        fun from(place: OriginPlace): Coordinate {
            return Coordinate(
                x = place.longitude,
                y = place.latitude,
            )
        }
    }
}
