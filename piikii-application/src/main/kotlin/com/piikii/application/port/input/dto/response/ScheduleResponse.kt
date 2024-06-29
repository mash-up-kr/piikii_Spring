package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.schedule.PlaceType
import com.piikii.application.domain.schedule.Schedule
import io.swagger.v3.oas.annotations.media.Schema

data class SchedulesResponse(
    @Schema(description = "방 카테고리 정보 목록")
    val categories: List<ScheduleResponse>,
) {
    companion object {
        fun from(schedules: List<Schedule>): SchedulesResponse {
            return SchedulesResponse(
                schedules.map { ScheduleResponse(it) },
            )
        }
    }
}

data class ScheduleResponse(
    @Schema(description = "방 카테고리의 id")
    val scheduleId: Long,
    @Schema(description = "방 카테고리의 이름")
    val name: String,
    @Schema(description = "방 카테고리의 카테고리")
    val placeType: PlaceType,
    @Schema(description = "방 카테고리의 순서")
    val sequence: Int,
) {
    constructor(schedule: Schedule) : this(
        scheduleId = schedule.id!!,
        name = schedule.name,
        placeType = schedule.placeType,
        sequence = schedule.sequence,
    )
}
