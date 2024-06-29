package com.piikii.application.port.input.room.dto.request

import com.fasterxml.jackson.annotation.JsonInclude
import com.piikii.application.domain.room.Password
import com.piikii.application.domain.room.Room
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
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
        )
    }
}
