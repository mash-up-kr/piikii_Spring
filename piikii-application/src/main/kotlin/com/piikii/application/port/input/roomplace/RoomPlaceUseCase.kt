package com.piikii.application.port.input.roomplace

import com.piikii.application.port.input.roomplace.dto.AddRoomPlaceRequest
import com.piikii.application.port.input.roomplace.dto.ModifyRoomPlaceRequest
import com.piikii.application.port.input.roomplace.dto.RoomPlaceCategoryGroupResponse
import com.piikii.application.port.input.roomplace.dto.RoomPlaceResponse
import java.util.UUID

interface RoomPlaceUseCase {
    fun addRoomPlace(
        targetRoomId: UUID,
        addRoomPlaceRequest: AddRoomPlaceRequest,
    ): RoomPlaceResponse

    fun retrieveAllByRoomId(roomId: UUID): List<RoomPlaceCategoryGroupResponse>

    fun modify(
        targetRoomPlaceId: Long,
        modifyRoomPlaceRequest: ModifyRoomPlaceRequest,
    )

    fun delete(targetRoomPlaceId: Long)
}
