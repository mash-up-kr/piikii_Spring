package com.piikii.application.port.input

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.port.input.dto.request.AddPlaceRequest
import com.piikii.application.port.input.dto.request.ModifyPlaceRequest
import com.piikii.application.port.input.dto.response.PlaceResponse
import com.piikii.application.port.input.dto.response.ScheduleTypeGroupResponse
import org.springframework.web.multipart.MultipartFile

interface PlaceUseCase {
    fun addPlace(
        targetRoomUid: UuidTypeId,
        addPlaceRequest: AddPlaceRequest,
        placeImages: List<MultipartFile>?,
    ): List<PlaceResponse>

    fun findAllByRoomUidGroupByPlaceType(roomUid: UuidTypeId): List<ScheduleTypeGroupResponse>

    fun modify(
        targetRoomUid: UuidTypeId,
        targetPlaceId: LongTypeId,
        modifyPlaceRequest: ModifyPlaceRequest,
        newPlaceImages: List<MultipartFile>?,
    ): PlaceResponse

    fun delete(targetPlaceId: LongTypeId)
}
