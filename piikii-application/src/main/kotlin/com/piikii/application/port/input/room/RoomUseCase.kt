package com.piikii.application.port.input.room

import com.piikii.application.port.input.room.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.room.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.room.dto.response.RoomGetResponseForm
import com.piikii.application.port.input.room.dto.response.RoomSaveResponseForm

interface RoomUseCase {
    fun save(request: RoomSaveRequestForm): RoomSaveResponseForm

    fun update(
        request: RoomUpdateRequestForm,
        roomId: Long,
    )

    fun delete(roomId: Long)

    fun retrieve(roomId: Long): RoomGetResponseForm
}
