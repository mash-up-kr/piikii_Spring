package com.piikii.application.port.output.persistence

import com.piikii.application.domain.place.Place
import java.util.UUID

interface PlaceQueryPort {
    fun findByPlaceId(placeId: Long): Place?

    fun findAllByPlaceIds(placeIds: List<Long>): List<Place>

    fun retrieveAllByRoomId(roomId: UUID): List<Place>
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
