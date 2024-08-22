package com.piikii.application.port.output.cache

import com.piikii.application.domain.course.CoursePlace
import com.piikii.application.domain.course.Distance
import com.piikii.application.domain.place.Place

interface CourseCachePort {
    fun getDistance(
        startPlace: CoursePlace?,
        endPlace: Place,
    ): Distance?
}
