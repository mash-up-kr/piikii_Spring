package com.piikii.application.port.input.room

import com.piikii.application.port.input.room.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.room.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.room.dto.response.RoomGetResponseForm
import com.piikii.application.port.input.room.dto.response.RoomSaveResponseForm

interface RoomUseCase {
    fun make(request: RoomSaveRequestForm): RoomSaveResponseForm

    fun modify(
        request: RoomUpdateRequestForm,
        roomId: Long,
    )

    fun remove(roomId: Long)

    fun search(roomId: Long): RoomGetResponseForm
}
