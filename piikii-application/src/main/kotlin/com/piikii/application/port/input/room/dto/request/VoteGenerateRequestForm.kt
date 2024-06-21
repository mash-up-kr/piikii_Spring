package com.piikii.application.port.input.room.dto.request

import com.piikii.application.domain.room.Room
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class VoteGenerateRequestForm(
    private val roomId: UUID,
    private val meetingName: String,
    private val meetDay: LocalDate,
    private val password: Short,
    private val voteDeadline: LocalDateTime,
) {
    fun toDomain(): Room {
        return Room(
            roomId = roomId,
            meetingName = meetingName,
            meetDay = meetDay,
            password = password,
            voteDeadline = voteDeadline,
        )
    }
}
