package com.piikii.application.port.input.room.dto.response

import com.piikii.application.domain.room.Room
import java.util.UUID

class RoomSaveResponseForm(
    private val roomId: UUID,
) {
    constructor(room: Room) : this(
        roomId = room.roomId!!,
    )
}
