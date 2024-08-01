package com.piikii.application.port.output.web

import com.piikii.application.domain.course.Coordinate
import com.piikii.application.domain.course.Distance

interface NavigationClient {
    fun getDistance(
        start: Coordinate,
        end: Coordinate,
    ): Distance
}
