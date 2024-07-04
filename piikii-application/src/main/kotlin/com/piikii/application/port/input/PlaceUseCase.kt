package com.piikii.application.port.input

import com.piikii.application.port.input.dto.request.AddPlaceRequest
import com.piikii.application.port.input.dto.request.ModifyPlaceRequest
import com.piikii.application.port.input.dto.response.PlaceResponse
import com.piikii.application.port.input.dto.response.PlaceTypeGroupResponse
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface PlaceUseCase {
    fun addPlace(
        roomId: UUID,
        addPlaceRequest: AddPlaceRequest,
        multipartFiles: List<MultipartFile>?,
    ): PlaceResponse

    fun findAllByRoomId(roomId: UUID): List<PlaceTypeGroupResponse>

    fun modify(
        roomId: UUID,
        targetPlaceId: Long,
        modifyPlaceRequest: ModifyPlaceRequest,
        newMultipartFiles: List<MultipartFile>?,
    ): PlaceResponse

    fun delete(targetPlaceId: Long)
}
