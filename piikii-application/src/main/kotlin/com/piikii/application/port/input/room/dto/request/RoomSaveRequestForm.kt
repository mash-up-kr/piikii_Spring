package com.piikii.application.port.input.room.dto.request

import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.room.Room
import java.time.LocalDate
import java.util.UUID

data class RoomSaveRequestForm(
    val meetingName: String,
    val message: String?,
    val address: String,
    val thumbnailLinks: List<String>,
    val password: Short,
    val meetDay: LocalDate,
) {
    fun toDomain(): Room {
        return Room(
            meetingName = this.meetingName,
            message = this.message,
            address = this.address,
            meetDay = this.meetDay,
            thumbnailLinks = ThumbnailLinks(this.thumbnailLinks),
            password = this.password,
            voteDeadline = null,
            roomId = UUID.randomUUID(),
        )
    }
}
