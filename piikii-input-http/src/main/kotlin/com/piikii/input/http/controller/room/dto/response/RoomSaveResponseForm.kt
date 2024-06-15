package com.piikii.input.http.controller.room.dto.response

import com.piikii.application.domain.room.Room
import java.util.UUID

class RoomSaveResponseForm(
    private val roomId: UUID,
)

fun Room.toRoomSaveResponse(): RoomSaveResponseForm {
    return RoomSaveResponseForm(
        roomId = this.roomId!!,
    )
}
