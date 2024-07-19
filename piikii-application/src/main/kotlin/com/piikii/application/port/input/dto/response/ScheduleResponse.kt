package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.domain.schedule.ScheduleType
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import io.swagger.v3.oas.annotations.media.Schema

data class SchedulesResponse(
    @Schema(description = "방 스케줄 정보 목록")
    val categories: List<ScheduleResponse>,
) {
    companion object {
        fun from(schedules: List<Schedule>): SchedulesResponse {
            return SchedulesResponse(
                schedules.map { ScheduleResponse.from(it) },
            )
        }
    }
}

data class ScheduleResponse(
    @Schema(description = "조회하려는 스케줄의 id")
    val scheduleId: Long,
    @Schema(description = "조회하려는 스케줄의 이름")
    val name: String,
    @Schema(description = "조회하려는 스케줄의 순서")
    val sequence: Int,
    @Schema(description = "조회하려는 스케줄의 타입")
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
                scheduleId = scheduleId,
                name = schedule.name,
                sequence = schedule.sequence,
                type = schedule.type,
            )
        }
    }
}
