package com.piikii.application.port.output.persistence

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.place.SchedulePlace

interface PlaceQueryPort {
    fun findByPlaceId(placeId: LongTypeId): Place

    fun findAllByPlaceIds(placeIds: List<LongTypeId>): List<Place>

    fun findAllByRoomUid(roomUid: UuidTypeId): List<Place>
}

interface PlaceCommandPort {
    fun save(
        roomUid: UuidTypeId,
        scheduleIds: List<LongTypeId>,
        place: Place,
    ): List<SchedulePlace>

    fun saveAll(
        roomUid: UuidTypeId,
        scheduleIds: List<LongTypeId>,
        places: List<Place>,
    ): List<Place>

    fun update(
        targetPlaceId: LongTypeId,
        place: Place,
    ): Place

    fun delete(targetPlaceId: LongTypeId)
}
