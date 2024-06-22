package com.piikii.application.port.input.room.dto.request

import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.room.Room
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class RoomUpdateRequestForm(
    private val meetingName: String,
    private val message: String?,
    private val address: String,
    private val thumbnailLinks: List<String>,
    private val password: Short,
    private val meetDay: LocalDate,
    private val voteDeadLine: LocalDateTime?,
    private val roomId: UUID,
) {
    fun toDomain(): Room {
        return Room(
            meetingName = this.meetingName,
            message = this.message,
            address = this.address,
            thumbnailLinks = ThumbnailLinks(this.thumbnailLinks),
            password = this.password,
            meetDay = meetDay,
            voteDeadline = this.voteDeadLine,
            roomId = this.roomId,
        )
    }
}
