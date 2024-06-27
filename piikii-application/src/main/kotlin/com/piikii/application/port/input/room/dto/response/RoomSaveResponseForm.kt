package com.piikii.application.port.input.room.dto.response

import com.piikii.application.domain.room.Room
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class RoomSaveResponseForm(
    @Schema(description = "방(Room) id")
    val roomId: UUID,
) {
    constructor(room: Room) : this(
        roomId = room.roomId,
    )
}
