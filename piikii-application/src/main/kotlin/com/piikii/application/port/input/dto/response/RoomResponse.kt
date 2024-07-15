package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.room.Room
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

data class RoomResponse(
    @Schema(description = "모임 이름")
    val name: String,
    @Schema(description = "적고 싶은 메시지")
    val message: String?,
    @Schema(description = "썸네일 이미지")
    val thumbnailLinks: String,
    @Schema(description = "투표 마감일")
    val voteDeadline: LocalDateTime?,
    @Schema(description = "방(Room) id")
    val roomUid: UUID,
) {
    companion object {
        fun from(room: Room): RoomResponse {
            return RoomResponse(
                name = room.name,
                message = room.message,
                thumbnailLinks = room.thumbnailLink,
                voteDeadline = room.voteDeadline,
                roomUid = room.roomUid,
            )
        }
    }
}

data class SaveRoomResponse(
    @Schema(description = "방(Room) id")
    val roomUid: UUID,
)
