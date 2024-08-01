package com.piikii.application.port.output.web

import com.piikii.application.domain.course.Distance

interface NavigationClient {
    fun getDistance(
        startX: Double?,
        startY: Double?,
        endX: Double?,
        endY: Double?,
    ): Distance
}
