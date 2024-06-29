package com.piikii.application.port.input.room.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.piikii.application.domain.room.Room
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RoomGetResponseForm(
    @Schema(description = "모임 이름")
    val meetingName: String,
    @Schema(description = "적고 싶은 메시지")
    val message: String?,
    @Schema(description = "모임 장소")
    val address: String,
    @Schema(description = "모임 날짜")
    val meetDay: LocalDate,
    @Schema(description = "썸네일 이미지")
    val thumbnailLinks: String,
    @Schema(description = "투표 마감일")
    val voteDeadline: LocalDateTime?,
    @Schema(description = "방(Room) id")
    val roomId: UUID,
) {
    constructor(room: Room) : this(
        meetingName = room.meetingName,
        message = room.message,
        address = room.address,
        meetDay = room.meetDay,
        thumbnailLinks = room.thumbnailLink,
        voteDeadline = room.voteDeadline,
        roomId = room.roomId,
    )
}
