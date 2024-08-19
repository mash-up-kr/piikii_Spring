package com.piikii.application.port.output.persistence

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.schedule.Schedule

interface ScheduleQueryPort {
    fun findAllByRoomUid(roomUid: UuidTypeId): List<Schedule>

    fun findById(id: LongTypeId): Schedule

    fun findAllByIds(ids: List<LongTypeId>): List<Schedule>
}

interface ScheduleCommandPort {
    fun saveSchedules(schedules: List<Schedule>)

    fun deleteSchedules(scheduleIds: List<LongTypeId>)

    fun updateSchedules(schedules: List<Schedule>)
}
