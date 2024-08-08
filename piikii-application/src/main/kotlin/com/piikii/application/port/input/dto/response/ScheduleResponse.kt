package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.domain.schedule.ScheduleType
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "방 스케줄 정보 응답")
data class SchedulesResponse(
    @field:Schema(description = "방 스케줄 정보 목록")
    val schedules: List<ScheduleResponse>,
) {
    companion object {
        fun from(schedules: List<Schedule>): SchedulesResponse {
            return SchedulesResponse(
                schedules.map { ScheduleResponse.from(it) },
            )
        }
    }
}

@Schema(description = "개별 스케줄 정보 응답")
data class ScheduleResponse(
    @field:Schema(description = "스케줄 ID", example = "1")
    val scheduleId: Long?,
    @field:Schema(description = "스케줄 이름", example = "술 1차")
    val name: String,
    @field:Schema(description = "스케줄 순서", example = "1")
    val sequence: Int,
    @field:Schema(
        description = "스케줄 타입",
        example = "DISH",
    )
    val type: ScheduleType,
) {
    companion object {
        fun from(schedule: Schedule): ScheduleResponse {
            val scheduleId =
                requireNotNull(schedule.id) {
                    throw PiikiiException(
                        exceptionCode = ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION,
                        detailMessage = "schedule Id can't be null",
                    )
                }
            return ScheduleResponse(
                scheduleId = scheduleId.getValue(),
                name = schedule.name,
                sequence = schedule.sequence,
                type = schedule.type,
            )
        }
    }
}
