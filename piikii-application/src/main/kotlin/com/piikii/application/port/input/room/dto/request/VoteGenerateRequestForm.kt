package com.piikii.application.port.input.room.dto.request

import java.time.LocalDateTime
import java.util.UUID

data class VoteGenerateRequestForm(
    val roomId: UUID,
    val voteDeadline: LocalDateTime,
)
