package com.piikii.application.port.output.persistence

import com.piikii.application.domain.place.Place
import java.util.UUID

interface PlaceQueryPort {
    fun retrieveByPlaceId(placeId: Long): Place?

    fun retrieveAllByRoomId(roomId: UUID): List<Place>

    fun findMostPopularPlaceByScheduleId(scheduleId: Long): Place
}

interface PlaceCommandPort {
    fun save(
        targetRoomId: UUID,
        place: Place,
    ): Place

    fun update(
        targetPlaceId: Long,
        place: Place,
    )

    fun delete(targetPlaceId: Long)
}
