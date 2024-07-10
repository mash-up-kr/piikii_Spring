package com.piikii.application.port.input

import com.piikii.application.port.input.dto.request.AddPlaceRequest
import com.piikii.application.port.input.dto.request.ModifyPlaceRequest
import com.piikii.application.port.input.dto.response.PlaceResponse
import com.piikii.application.port.input.dto.response.PlaceTypeGroupResponse
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface PlaceUseCase {
    fun addPlace(
        targetRoomId: UUID,
        addPlaceRequest: AddPlaceRequest,
        placeImages: List<MultipartFile>?,
    ): PlaceResponse

    fun findAllByRoomIdGroupByPlaceType(targetRoomId: UUID): List<PlaceTypeGroupResponse>

    fun modify(
        targetRoomId: UUID,
        targetPlaceId: Long,
        modifyPlaceRequest: ModifyPlaceRequest,
        newPlaceImages: List<MultipartFile>?,
    ): PlaceResponse

    fun delete(targetPlaceId: Long)
}
