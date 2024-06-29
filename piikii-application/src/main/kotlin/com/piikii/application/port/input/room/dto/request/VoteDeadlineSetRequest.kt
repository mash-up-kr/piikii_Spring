package com.piikii.application.port.input.room.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

data class VoteDeadlineSetRequest(
    @Schema(description = "방(Room) id")
    val roomId: UUID,
    @Schema(description = "투표 마감일")
    val voteDeadline: LocalDateTime,
)
