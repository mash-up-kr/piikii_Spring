package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.schedule.PlaceType
import com.piikii.application.domain.schedule.Schedule
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class DeleteSchedulesRequest(
    @Schema(description = "삭제할 방 카테고리 id 목록")
    val scheduleIds: List<Long>,
)

data class CreateSchedulesRequest(
    @Schema(description = "추가할 방 카테고리 목록")
    val categories: List<CreateScheduleRequest>,
) {
    fun toDomains(roomUid: UUID): List<Schedule> {
        return categories.map { it.toDomain(roomUid) }
    }
}

data class CreateScheduleRequest(
    @Schema(description = "방 카테고리의 이름")
    val name: String,
    @Schema(description = "방 카테고리의 카테고리")
    val placeType: PlaceType,
    @Schema(description = "방 카테고리의 순서")
    val sequence: Int,
) {
    fun toDomain(roomUid: UUID): Schedule {
        return Schedule(
            id = 0L,
            roomUid = roomUid,
            name = this.name,
            sequence = this.sequence,
        )
    }
}
