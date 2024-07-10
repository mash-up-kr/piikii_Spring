package com.piikii.application.port.output.persistence

import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.PlaceType
import java.util.UUID

interface PlaceQueryPort {
    fun findByPlaceId(placeId: Long): Place?

    fun findAllByPlaceIds(placeIds: List<Long>): List<Place>

    fun findAllByRoomIdGroupByPlaceType(roomId: UUID): Map<PlaceType, List<Place>>
}

interface PlaceCommandPort {
    fun save(
        roomId: UUID,
        scheduleId: Long,
        place: Place,
    ): Place

    fun update(
        targetPlaceId: Long,
        place: Place,
    ): Place

    fun delete(targetPlaceId: Long)
}
