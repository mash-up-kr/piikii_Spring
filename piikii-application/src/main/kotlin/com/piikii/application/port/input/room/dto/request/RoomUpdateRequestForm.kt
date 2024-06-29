package com.piikii.application.port.input.room.dto.request

import com.piikii.application.domain.room.Room
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class RoomUpdateRequestForm(
    @Schema(description = "모임 이름")
    val meetingName: String,
    @Schema(description = "적고 싶은 메시지")
    val message: String?,
    @Schema(description = "모임 장소")
    val address: String,
    @Schema(description = "썸네일 이미지")
    val thumbnailLink: String,
    @Schema(description = "모임 비밀번호")
    val password: Short,
    @Schema(description = "모임 날짜")
    val meetDay: LocalDate,
    @Schema(description = "투표 마감일")
    val voteDeadLine: LocalDateTime?,
    @Schema(description = "방(Room) id")
    val roomId: UUID,
) {
    fun toDomain(): Room {
        return Room(
            meetingName = this.meetingName,
            message = this.message,
            address = this.address,
            thumbnailLink = this.thumbnailLink,
            password = this.password,
            meetDay = meetDay,
            voteDeadline = this.voteDeadLine,
            roomId = this.roomId,
        )
    }
}
