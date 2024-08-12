package com.piikii.application.domain.fixture

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.domain.schedule.ScheduleType
import java.util.UUID

class ScheduleFixture(
    private var id: LongTypeId = LongTypeId(0L),
    private var roomUid: UuidTypeId = UuidTypeId(UUID.randomUUID()),
    private var name: String = "1차",
    private var sequence: Int = 0,
    private var type: ScheduleType = ScheduleType.DISH,
) {
    fun id(id: Long): ScheduleFixture {
        this.id = LongTypeId(id)
        return this
    }

    fun roomUid(roomUid: UuidTypeId): ScheduleFixture {
        this.roomUid = roomUid
        return this
    }

    fun name(name: String): ScheduleFixture {
        this.name = name
        return this
    }

    fun sequence(sequence: Int): ScheduleFixture {
        this.sequence = sequence
        return this
    }

    fun type(type: ScheduleType): ScheduleFixture {
        this.type = type
        return this
    }

    fun build(): Schedule {
        return Schedule(
            id = this.id,
            roomUid = this.roomUid,
            name = this.name,
            sequence = this.sequence,
            type = this.type,
        )
    }

    companion object {
        fun create() = ScheduleFixture()
    }
}
