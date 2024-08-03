package com.piikii.application.domain.course

import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.domain.schedule.ScheduleType

data class CoursePlace(
    val scheduleId: Long,
    val scheduleType: ScheduleType,
    val placeId: Long,
    val name: String,
    val url: String?,
    val address: String?,
    val phoneNumber: String?,
    val coordinate: Coordinate?,
    val distance: Distance?,
) {
    companion object {
        fun from(
            schedule: Schedule,
            place: Place,
            coordinate: Coordinate?,
            distance: Distance?,
        ): CoursePlace {
            return CoursePlace(
                scheduleId = schedule.id!!,
                scheduleType = schedule.type,
                placeId = place.id,
                name = place.name,
                url = place.url,
                address = place.address,
                phoneNumber = place.phoneNumber,
                coordinate = coordinate,
                distance = distance,
            )
        }
    }
}
