package com.piikii.application.port.output.persistence

import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.Schedule
import java.util.UUID

interface PlaceQueryPort {
    fun findByPlaceId(placeId: Long): Place?

    fun findAllByPlaceIds(placeIds: List<Long>): List<Place>

    fun findAllByRoomId(roomId: UUID): List<Place>

    fun findAllWithSchedulesByRoomId(roomId: UUID): List<Pair<Place, Schedule>>
}

interface PlaceCommandPort {
    fun save(
        targetRoomId: UUID,
        scheduleId: Long,
        place: Place,
    ): Place

    fun update(
        targetPlaceId: Long,
        place: Place,
    ): Place

    fun delete(targetPlaceId: Long)
}
