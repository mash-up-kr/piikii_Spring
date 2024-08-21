package com.piikii.output.redis.adapter

import com.piikii.application.domain.course.CoursePlace
import com.piikii.application.domain.course.Distance
import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.place.Place
import com.piikii.application.port.output.cache.CourseCachePort
import com.piikii.application.port.output.web.NavigationPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class CourseRedisCacheAdapter(
    private val navigationPort: NavigationPort,
    private val redisTemplate: RedisTemplate<String, Any>
): CourseCachePort {

    @Cacheable(
        value = ["Distances"],
        key = "#key",
        cacheManager = "cacheManager",
        condition = "#key != null",
    )
    override fun getDistance(startPlace: CoursePlace?, endPlace: Place): Distance? {
        val key = getKey(startPlace?.placeId, endPlace.id)
        println(key)
        println(redisTemplate.keys("*"))
        return startPlace?.coordinate?.let { preCoordinate ->
            endPlace.getCoordinate().let { coordinate ->
                navigationPort.getDistance(start = preCoordinate, end = coordinate)
            }
        }
    }

    private fun getKey(startPlaceId: LongTypeId?, endPlaceId: LongTypeId): String? {
        return startPlaceId?.let { "${it.getValue()}_${endPlaceId.getValue()}" }
    }
}
