package com.piikii.application.port.input

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.room.Password
import com.piikii.application.port.input.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.dto.response.RoomResponse
import com.piikii.application.port.input.dto.response.SaveRoomResponse
import java.time.LocalDateTime

interface RoomUseCase {
    fun create(request: RoomSaveRequestForm): SaveRoomResponse

    fun modify(request: RoomUpdateRequestForm)

    fun remove(roomUid: UuidTypeId)

    fun findById(roomUid: UuidTypeId): RoomResponse

    fun changeVoteDeadline(
        roomUid: UuidTypeId,
        voteDeadline: LocalDateTime,
    )

    fun verifyPassword(
        roomUid: UuidTypeId,
        password: Password,
    )
}
