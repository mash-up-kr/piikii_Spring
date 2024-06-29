package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.room.Password
import com.piikii.application.domain.room.Room
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class RoomSaveRequestForm(
    @Schema(description = "모임 이름")
    val meetingName: String,
    @Schema(description = "적고 싶은 메시지")
    val message: String?,
    @Schema(description = "모임 장소")
    val address: String,
    @Schema(description = "썸네일 이미지")
    val thumbnailLink: String,
    @Schema(description = "모임 비밀번호")
    @Size(max = 4, message = "Data must be 4 characters or less")
    val password: Password,
    @Schema(description = "모임 날짜")
    val meetDay: LocalDate,
) {
    fun toDomain(): Room {
        return Room(
            meetingName = this.meetingName,
            message = this.message,
            address = this.address,
            meetDay = this.meetDay,
            thumbnailLink = this.thumbnailLink,
            password = this.password,
            voteDeadline = null,
            roomId = UUID.randomUUID(),
            isCourseCreated = false,
        )
    }
}

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
    @Size(max = 4, message = "Data must be 4 characters or less")
    val password: Password,
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
            isCourseCreated = false,
            // TODO: 따로 이슈 파서 수정
        )
    }
}
