package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.output.persistence.postgresql.persistence.entity.SchedulePlaceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SchedulePlaceRepository : JpaRepository<SchedulePlaceEntity, Long> {
    fun findAllByRoomUid(roomUid: UuidTypeId): List<SchedulePlaceEntity>

    fun findAllByPlaceId(placeId: LongTypeId): List<SchedulePlaceEntity>

    fun deleteAllByPlaceIdAndScheduleIdIn(
        placeId: LongTypeId,
        scheduleIds: List<Long>,
    )

    fun deleteAllByPlaceId(placeId: LongTypeId)

    fun findAllByScheduleIdIn(scheduleIds: List<Long>): List<SchedulePlaceEntity>

    fun deleteAllByScheduleIdIn(scheduleIds: List<Long>)

    fun findAllByRoomUidAndConfirmed(
        roomUid: UuidTypeId,
        isConfirmed: Boolean,
    ): List<SchedulePlaceEntity>

    fun findByRoomUidAndPlaceId(
        roomUid: UuidTypeId,
        placeId: LongTypeId,
    ): SchedulePlaceEntity?

    fun findByScheduleIdAndConfirmed(
        scheduleId: LongTypeId,
        isConfirmed: Boolean,
    ): SchedulePlaceEntity?

    fun findByRoomUidAndPlaceIdIn(
        roomUid: UuidTypeId,
        scheduleIds: List<Long>,
    ): List<SchedulePlaceEntity>

    fun findAllByIdIn(ids: List<Long>): List<SchedulePlaceEntity>
}
