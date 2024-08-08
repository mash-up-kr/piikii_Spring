package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.domain.schedule.ScheduleType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class RegisterSchedulesRequest(
    @field:NotEmpty(message = "최소 하나 이상의 스케줄을 등록해야 합니다.")
    @field:Valid
    @field:Schema(description = "등록하려는 방 스케줄 목록")
    val schedules: List<RegisterScheduleRequest>,
) {
    fun toDomains(roomUid: UuidTypeId): List<Schedule> {
        return schedules.map { it.toDomain(roomUid) }
    }
}

data class RegisterScheduleRequest(
    @field:Schema(description = "등록하려는 스케줄의 id", example = "1")
    val scheduleId: Long?,
    @field:NotNull(message = "스케줄 이름은 필수입니다.")
    @field:Schema(description = "등록하려는 스케줄의 이름", example = "점심 식사")
    val name: String,
    @field:NotNull(message = "스케줄 타입은 필수입니다.")
    @field:Schema(
        description = "스케줄 타입",
        example = "DISH",
    )
    val type: ScheduleType,
    @field:NotNull(message = "스케줄 순서는 필수입니다.")
    @field:Positive(message = "스케줄 순서는 양수여야 합니다.")
    @field:Schema(description = "등록하려는 스케줄의 순서", example = "1")
    val sequence: Int,
) {
    fun toDomain(roomUid: UuidTypeId): Schedule {
        return Schedule(
            id = LongTypeId(this.scheduleId),
            roomUid = roomUid,
            name = this.name,
            sequence = this.sequence,
            type = ScheduleType.valueOf(this.type.name),
        )
    }
}
