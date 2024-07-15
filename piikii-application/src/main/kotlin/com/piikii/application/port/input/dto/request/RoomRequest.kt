package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.room.Password
import com.piikii.application.domain.room.Room
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.UUID

data class RoomSaveRequestForm(
    @Schema(description = "모임 이름")
    val name: String,
    @Schema(description = "적고 싶은 메시지")
    val message: String?,
    @Schema(description = "모임 장소")
    val address: String,
    @Schema(description = "썸네일 이미지")
    val thumbnailLink: String,
    @Schema(description = "모임 비밀번호")
    @Size(max = 4, message = "Data must be 4 characters or less")
    val password: Password,
) {
    fun toDomain(): Room {
        return Room(
            roomUid = UUID.randomUUID(),
            name = this.name,
            message = this.message,
            thumbnailLink = this.thumbnailLink,
            password = this.password,
            voteDeadline = null,
        )
    }
}

data class RoomUpdateRequestForm(
    @Schema(description = "방(Room) id")
    val roomUid: UUID,
    @Schema(description = "모임 이름")
    val name: String,
    @Schema(description = "적고 싶은 메시지")
    val message: String?,
    @Schema(description = "썸네일 이미지")
    val thumbnailLink: String,
    @Schema(description = "모임 비밀번호")
    @Size(max = 4, message = "Data must be 4 characters or less")
    val password: Password,
    @Schema(description = "투표 마감일")
    val voteDeadLine: LocalDateTime?,
) {
    fun toDomain(): Room {
        return Room(
            roomUid = this.roomUid,
            name = this.name,
            message = this.message,
            thumbnailLink = this.thumbnailLink,
            password = this.password,
            voteDeadline = this.voteDeadLine,
        )
    }
}
