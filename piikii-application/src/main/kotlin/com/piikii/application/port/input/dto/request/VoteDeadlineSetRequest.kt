package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.room.Password
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class VoteDeadlineSetRequest(
    @field:NotNull(message = "투표 마감일은 필수입니다.")
    @field:Future(message = "투표 마감일은 현재 시간 이후여야 합니다.")
    @field:Schema(description = "투표 마감일", example = "2024-12-31T23:59:59")
    val voteDeadline: LocalDateTime,
    @field:NotNull(message = "방 비밀번호는 필수입니다.")
    @field:Size(min = 4, max = 4, message = "비밀번호는 반드시 4자리여야 합니다.")
    @field:Schema(description = "방 비밀번호", example = "1234")
    val password: Password,
)
