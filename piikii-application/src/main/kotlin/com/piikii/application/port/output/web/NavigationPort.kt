package com.piikii.application.port.output.web

import com.piikii.application.domain.course.Coordinate
import com.piikii.application.domain.course.Distance

interface NavigationPort {
    fun getDistance(
        start: Coordinate,
        end: Coordinate,
    ): Distance
}
