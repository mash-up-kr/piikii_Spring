package com.piikii.application.port.input.room.dto.request

import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.room.Room
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class RoomUpdateRequestForm(
    val meetingName: String,
    val message: String?,
    val address: String,
    val thumbnailLinks: List<String>,
    val password: Short,
    val meetDay: LocalDate,
    val voteDeadLine: LocalDateTime?,
    val roomId: UUID,
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
