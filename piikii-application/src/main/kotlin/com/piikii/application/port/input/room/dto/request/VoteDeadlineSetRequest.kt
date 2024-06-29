package com.piikii.application.port.input.room.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class VoteDeadlineSetRequest(
    @Schema(description = "투표 마감일")
    val voteDeadline: LocalDateTime,
)
