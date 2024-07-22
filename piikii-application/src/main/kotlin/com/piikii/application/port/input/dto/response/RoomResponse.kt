package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.room.Room
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

@Schema(description = "모임 정보 응답")
data class RoomResponse(
    @field:Schema(description = "모임 이름", example = "이번엔 좀 모이자")
    val name: String,
    @field:Schema(description = "적고 싶은 메시지", example = "모여라...")
    val message: String?,
    @field:Schema(description = "썸네일 이미지 링크", example = "https://example.com/thumbnail.jpg")
    val thumbnailLinks: String,
    @field:Schema(description = "투표 마감일", example = "2024-12-31T23:59:59")
    val voteDeadline: LocalDateTime?,
    @field:Schema(description = "방(Room) 고유 식별자", example = "123e4567-e89b-12d3-a456-426614174000")
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

@Schema(description = "모임 생성 응답")
data class SaveRoomResponse(
    @field:Schema(description = "생성된 방(Room)의 고유 식별자", example = "123e4567-e89b-12d3-a456-426614174000")
    val roomUid: UUID,
)
