package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.room.Password
import com.piikii.application.domain.room.Room
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.UUID

data class RoomSaveRequestForm(
    @field:NotBlank(message = "모임 이름은 필수이며 빈 문자열이 허용되지 않습니다.")
    @field:Schema(description = "모임 이름", example = "맨날 입으로만 모이자고 하던 애들 모임")
    val name: String,
    @field:Schema(description = "적고 싶은 메시지", example = "늦으면 밥값 몰빵")
    val message: String?,
    @field:Schema(description = "썸네일 이미지 링크", example = "https://github.com/k-diger.png")
    val thumbnailLink: String,
    @field:NotNull(message = "모임 비밀번호는 필수입니다.")
    @field:Schema(description = "모임 비밀번호", example = "0001")
    @field:Size(max = 4, message = "비밀번호는 반드시 4자리여야합니다.")
    val password: Password,
) {
    fun toDomain(): Room {
        return Room(
            roomUid = UuidTypeId(UUID.randomUUID()),
            name = this.name,
            message = this.message,
            thumbnailLink = this.thumbnailLink,
            password = this.password,
            voteDeadline = null,
        )
    }
}

data class RoomUpdateRequestForm(
    @field:NotNull(message = "수정하고자 하는 방의 Uid값은 필수입니다.")
    @field:Schema(description = "방(Room) id", example = "123e4567-e89b-12d3-a456-426614174000")
    val roomUid: UUID,
    @field:NotBlank(message = "모임 이름은 필수이며 빈 문자열이 허용되지 않습니다.")
    @field:Schema(description = "모임 이름", example = "이번엔 진짜 모이자")
    val name: String,
    @field:Schema(description = "적고 싶은 메시지", example = "안모이면 5콩")
    val message: String?,
    @field:Schema(description = "썸네일 이미지 링크", example = "https://github.com/k-diger.png")
    val thumbnailLink: String,
    @field:NotNull(message = "모임 비밀번호는 필수입니다.")
    @field:Schema(description = "모임 비밀번호", example = "1234")
    @field:Size(max = 4, message = "비밀번호는 반드시 4자리여야합니다.")
    val password: Password,
    @field:Schema(description = "투표 마감일", example = "2024-08-01T18:00:00")
    val voteDeadLine: LocalDateTime?,
) {
    fun toDomain(): Room {
        return Room(
            roomUid = UuidTypeId(this.roomUid),
            name = this.name,
            message = this.message,
            thumbnailLink = this.thumbnailLink,
            password = this.password,
            voteDeadline = this.voteDeadLine,
        )
    }
}

data class RoomPasswordVerifyRequestForm(
    @field:NotNull(message = "방 비밀번호는 필수입니다.")
    @field:Size(min = 4, max = 4, message = "비밀번호는 반드시 4자리여야 합니다.")
    @field:Schema(description = "방 비밀번호", example = "1234")
    val password: Password,
)
