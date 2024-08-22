package com.piikii.application.port.output.persistence

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.place.SchedulePlace

interface SchedulePlaceQueryPort {
    fun findAllByRoomUid(roomUid: UuidTypeId): List<SchedulePlace>

    fun findAllByPlaceId(placeId: LongTypeId): List<SchedulePlace>

    fun findAllConfirmedByRoomId(roomId: UuidTypeId): List<SchedulePlace>

    fun findByRoomUidAndPlaceId(
        roomUid: UuidTypeId,
        placeId: LongTypeId,
    ): SchedulePlace

    fun findConfirmedByScheduleId(scheduleId: LongTypeId): SchedulePlace?

    fun findAllByRoomUidAndPaceIds(
        roomUid: UuidTypeId,
        placeIds: List<LongTypeId>,
    ): List<SchedulePlace>

    fun findById(id: LongTypeId): SchedulePlace

    fun findAllByIdIn(ids: List<LongTypeId>): List<SchedulePlace>
}

interface SchedulePlaceCommandPort {
    fun saveAll(schedulePlaces: List<SchedulePlace>)

    fun deleteAllByPlaceIdAndScheduleIds(
        placeId: LongTypeId,
        scheduleIds: List<LongTypeId>,
    )

    fun deleteAllByPlaceId(placeId: LongTypeId)

    fun update(
        targetId: LongTypeId,
        schedulePlace: SchedulePlace,
    )
}
