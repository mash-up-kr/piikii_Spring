package com.piikii.application.port.input.room.dto.request

import com.piikii.application.domain.room.Room
import java.time.LocalDate
import java.time.LocalDateTime

class RoomSaveRequestForm(
    private val meetingName: String,
    private val message: String?,
    private val password: Short,
    private val meetDay: LocalDate,
    private val voteDeadLine: LocalDateTime,
) {
    fun toDomain(): Room {
        return Room(
            meetingName = this.meetingName,
            message = this.message,
            password = this.password,
            meetDay = this.meetDay,
            voteDeadline = this.voteDeadLine,
        )
    }
}
