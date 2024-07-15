package com.piikii.application.port.output.persistence

import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.ScheduleType
import java.util.UUID

interface PlaceQueryPort {
    fun findByPlaceId(placeId: Long): Place?

    fun findAllByPlaceIds(placeIds: List<Long>): List<Place>

    fun findAllByroomUidGroupByPlaceType(roomUid: UUID): Map<ScheduleType, List<Place>>
}

interface PlaceCommandPort {
    fun save(
        roomUid: UUID,
        scheduleId: Long,
        place: Place,
    ): Place

    fun update(
        targetPlaceId: Long,
        place: Place,
    ): Place

    fun delete(targetPlaceId: Long)
}
