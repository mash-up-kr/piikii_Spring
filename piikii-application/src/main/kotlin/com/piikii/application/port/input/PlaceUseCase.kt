package com.piikii.application.port.input

import com.piikii.application.port.input.dto.request.AddPlaceRequest
import com.piikii.application.port.input.dto.request.ModifyPlaceRequest
import com.piikii.application.port.input.dto.response.PlaceResponse
import com.piikii.application.port.input.dto.response.PlaceTypeGroupResponse
import java.util.UUID

interface PlaceUseCase {
    fun addPlace(
        roomId: UUID,
        addPlaceRequest: AddPlaceRequest,
    ): PlaceResponse

    fun findAllByRoomId(roomId: UUID): List<PlaceTypeGroupResponse>

    fun modify(
        roomId: UUID,
        targetPlaceId: Long,
        modifyPlaceRequest: ModifyPlaceRequest,
    )

    fun delete(targetPlaceId: Long)
}
