package com.piikii.application.domain.fixture

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.place.SchedulePlace
import java.util.UUID

class SchedulePlaceFixture(
    private var id: LongTypeId = LongTypeId(0L),
    private var roomUid: UuidTypeId = UuidTypeId(UUID.randomUUID()),
    private var scheduleId: LongTypeId = LongTypeId(0L),
    private var placeId: LongTypeId = LongTypeId(0L),
    private var confirmed: Boolean = false,
) {
    fun id(id: Long): SchedulePlaceFixture {
        this.id = LongTypeId(id)
        return this
    }

    fun roomUid(roomUid: UuidTypeId): SchedulePlaceFixture {
        this.roomUid = roomUid
        return this
    }

    fun scheduleId(scheduleId: LongTypeId): SchedulePlaceFixture {
        this.scheduleId = scheduleId
        return this
    }

    fun placeId(placeId: LongTypeId): SchedulePlaceFixture {
        this.placeId = placeId
        return this
    }

    fun confirmed(confirmed: Boolean): SchedulePlaceFixture {
        this.confirmed = confirmed
        return this
    }

    fun build(): SchedulePlace {
        return SchedulePlace(
            id = this.id,
            roomUid = this.roomUid,
            scheduleId = this.scheduleId,
            placeId = this.placeId,
            confirmed = this.confirmed,
        )
    }

    companion object {
        fun create() = SchedulePlaceFixture()
    }
}
