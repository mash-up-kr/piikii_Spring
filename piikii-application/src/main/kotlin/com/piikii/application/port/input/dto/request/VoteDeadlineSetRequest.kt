package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.room.Password
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class VoteDeadlineSetRequest(
    @Schema(description = "투표 마감일")
    val voteDeadline: LocalDateTime,
    @Schema(description = "방 패스워드")
    @Size(max = 4, message = "Data must be 4 characters or less")
    val password: Password,
)
