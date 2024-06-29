package com.piikii.application.port.input.room

import com.piikii.application.port.input.room.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.room.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.room.dto.response.RoomGetResponseForm
import com.piikii.application.port.input.room.dto.response.RoomSaveResponseForm
import java.time.LocalDateTime
import java.util.UUID

interface RoomUseCase {
    fun generate(request: RoomSaveRequestForm): RoomSaveResponseForm

    fun modify(request: RoomUpdateRequestForm)

    fun remove(roomId: UUID)

    fun search(roomId: UUID): RoomGetResponseForm

    fun changeVoteDeadline(
        roomId: UUID,
        voteDeadline: LocalDateTime,
    )
}
