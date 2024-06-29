package com.piikii.input.http.controller

import com.piikii.application.port.input.roomplace.RoomPlaceUseCase
import com.piikii.application.port.input.roomplace.dto.AddRoomPlaceRequest
import com.piikii.application.port.input.roomplace.dto.DeleteRoomPlaceRequest
import com.piikii.application.port.input.roomplace.dto.ModifyRoomPlaceRequest
import com.piikii.application.port.input.roomplace.dto.RoomPlaceCategoryGroupResponse
import com.piikii.application.port.input.roomplace.dto.RoomPlaceResponse
import com.piikii.input.http.docs.RoomPlaceDocs
import com.piikii.input.http.dto.ResponseForm
import com.piikii.input.http.dto.RoomMessage.SUCCESS_CREATE_ROOM_PLACE
import com.piikii.input.http.dto.RoomMessage.SUCCESS_DELETE_ROOM_PLACE
import com.piikii.input.http.dto.RoomMessage.SUCCESS_UPDATE_ROOM_PLACE
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/room/")
class RoomPlaceApi(
    private val roomPlaceUseCase: RoomPlaceUseCase,
) : RoomPlaceDocs {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{roomId}/place")
    override fun addRoomPlace(
        @PathVariable("roomId") roomId: UUID,
        @RequestBody addRoomPlaceRequest: AddRoomPlaceRequest,
    ): ResponseForm<RoomPlaceResponse> {
        return ResponseForm(
            data = roomPlaceUseCase.addRoomPlace(roomId, addRoomPlaceRequest),
            message = SUCCESS_CREATE_ROOM_PLACE,
        )
    }

    @GetMapping("/{roomId}/place")
    override fun retrieveAll(
        @PathVariable("roomId") roomId: UUID,
    ): ResponseForm<List<RoomPlaceCategoryGroupResponse>> {
        return ResponseForm(
            data = roomPlaceUseCase.retrieveAllByRoomId(roomId),
            message = "",
        )
    }

    @PatchMapping("/{roomId}/place/{targetRoomPlaceId}")
    override fun modifyRoomPlace(
        @PathVariable("roomId") roomId: UUID,
        @PathVariable("targetRoomPlaceId") targetRoomPlaceId: Long,
        @RequestBody modifyRoomPlaceRequest: ModifyRoomPlaceRequest,
    ): ResponseForm<Any> {
        return ResponseForm(
            data = roomPlaceUseCase.modify(targetRoomPlaceId, modifyRoomPlaceRequest),
            message = SUCCESS_UPDATE_ROOM_PLACE,
        )
    }

    @DeleteMapping("/{roomId}/place")
    override fun deleteRoomPlace(
        @PathVariable("roomId") roomId: UUID,
        @RequestBody deleteRoomPlaceRequest: DeleteRoomPlaceRequest,
    ): ResponseForm<Any> {
        return ResponseForm(
            data = roomPlaceUseCase.delete(deleteRoomPlaceRequest.targetRoomPlaceId),
            message = SUCCESS_DELETE_ROOM_PLACE,
        )
    }
}
