package com.piikii.application.port.output.persistence

import com.piikii.application.domain.schedule.Schedule
import java.util.UUID

interface ScheduleQueryPort {
    fun findSchedulesByRoomId(roomId: UUID): List<Schedule>
}

interface ScheduleCommandPort {
    fun saveSchedules(schedules: List<Schedule>)

    fun deleteSchedules(scheduleIds: List<Long>)
}
