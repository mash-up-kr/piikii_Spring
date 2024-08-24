package com.piikii.application.domain.course

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.domain.schedule.ScheduleType

data class CoursePlace(
    val scheduleId: LongTypeId,
    val scheduleType: ScheduleType,
    val prePlace: Place?,
    val place: Place,
    val coordinate: Coordinate,
    val distance: Distance,
) {
    constructor(schedule: Schedule, prePlace: Place?, place: Place, distance: Distance) : this(
        scheduleId = schedule.id,
        scheduleType = schedule.type,
        prePlace = prePlace,
        place = place,
        coordinate = place.getCoordinate(),
        distance = distance,
    )
}
