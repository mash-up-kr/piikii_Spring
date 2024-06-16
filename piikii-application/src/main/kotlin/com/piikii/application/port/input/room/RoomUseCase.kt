package com.piikii.application.port.input.room

import com.piikii.application.port.input.room.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.room.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.room.dto.response.RoomGetResponseForm
import com.piikii.application.port.input.room.dto.response.RoomSaveResponseForm

interface RoomUseCase {
    fun makeRoom(request: RoomSaveRequestForm): RoomSaveResponseForm

    fun modifyRoom(
        request: RoomUpdateRequestForm,
        roomId: Long,
    )

    fun removeRoom(roomId: Long)

    fun searchRoom(roomId: Long): RoomGetResponseForm
}
