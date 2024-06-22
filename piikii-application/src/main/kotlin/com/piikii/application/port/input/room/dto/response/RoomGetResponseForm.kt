package com.piikii.application.port.input.room.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.piikii.application.domain.room.Room
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RoomGetResponseForm(
    val meetingName: String,
    val message: String?,
    val address: String,
    val meetDay: LocalDate,
    val thumbnailLinks: List<String>,
    val voteDeadline: LocalDateTime?,
    val roomId: UUID,
) {
    constructor(room: Room) : this(
        meetingName = room.meetingName,
        message = room.message,
        address = room.address,
        meetDay = room.meetDay,
        thumbnailLinks = room.thumbnailLinks.convertToList,
        voteDeadline = room.voteDeadline,
        roomId = room.roomId,
    )
}
