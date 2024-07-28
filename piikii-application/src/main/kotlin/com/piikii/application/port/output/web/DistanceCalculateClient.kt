package com.piikii.application.port.output.web

import com.piikii.application.domain.course.Distance

interface DistanceCalculateClient {
    fun getDistanceBetweenPlaces(
        startX: Double?,
        startY: Double?,
        endX: Double?,
        endY: Double?,
    ): Distance
}
