package com.piikii.application.port.input.room

import com.piikii.application.domain.room.Room
import com.piikii.application.port.input.room.dto.response.RoomGetResponseForm
import com.piikii.application.port.input.room.dto.response.RoomSaveResponseForm

interface RoomUseCase {
    fun save(request: Room): RoomSaveResponseForm

    fun update(
        request: Room,
        roomId: Long,
    )

    fun delete(roomId: Long)

    fun retrieve(roomId: Long): RoomGetResponseForm
}
