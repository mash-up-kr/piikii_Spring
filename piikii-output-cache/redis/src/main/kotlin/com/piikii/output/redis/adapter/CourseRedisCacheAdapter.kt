package com.piikii.output.redis.adapter

import com.piikii.application.domain.course.CoursePlace
import com.piikii.application.domain.course.Distance
import com.piikii.application.domain.place.Place
import com.piikii.application.port.output.cache.CourseCachePort
import com.piikii.application.port.output.web.NavigationPort
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class CourseRedisCacheAdapter(
    private val navigationPort: NavigationPort,
) : CourseCachePort {
    @Cacheable(
        value = ["Distances"],
        key = "#startPlace?.placeId + '_' + #endPlace.id",
        cacheManager = "cacheManager",
        condition = "#startPlace != null",
        unless = "#result == null",
    )
    override fun getDistance(
        startPlace: CoursePlace?,
        endPlace: Place,
    ): Distance? {
        return startPlace?.coordinate?.let { preCoordinate ->
            endPlace.getCoordinate().let { coordinate ->
                navigationPort.getDistance(start = preCoordinate, end = coordinate)
            }
        }
    }
}
