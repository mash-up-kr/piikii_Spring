package com.piikii.application.port.output.web

import com.piikii.application.domain.course.Distance
import com.piikii.application.domain.place.Place

interface NavigationPort {
    fun getDistance(
        startPlace: Place,
        endPlace: Place,
    ): Distance
}
