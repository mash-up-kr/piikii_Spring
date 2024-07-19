package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.domain.schedule.ScheduleType
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class RegisterSchedulesRequest(
    @Schema(description = "등록하려는 방 스케줄 목록")
    val schedules: List<RegisterScheduleRequest>,
) {
    fun toDomains(roomUid: UUID): List<Schedule> {
        return schedules.map { it.toDomain(roomUid) }
    }
}

data class RegisterScheduleRequest(
    @Schema(description = "등록하려는 스케줄의 id")
    val scheduleId: Long?,
    @Schema(description = "등록하려는 스케줄의 이름")
    val name: String,
    @Schema(description = "등록하려는 스케줄의 타입")
    val scheduleType: ScheduleType,
    @Schema(description = "등록하려는 스케줄의 순서")
    val sequence: Int,
) {
    fun toDomain(roomUid: UUID): Schedule {
        return Schedule(
            id = this.scheduleId,
            roomUid = roomUid,
            name = this.name,
            sequence = this.sequence,
            type = ScheduleType.valueOf(this.scheduleType.name),
        )
    }
}
