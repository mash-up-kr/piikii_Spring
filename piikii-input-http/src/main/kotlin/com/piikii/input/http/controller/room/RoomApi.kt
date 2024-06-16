package com.piikii.input.http.controller.room

import com.piikii.application.port.input.room.RoomUseCase
import com.piikii.application.port.input.room.dto.response.RoomGetResponseForm
import com.piikii.application.port.input.room.dto.response.RoomSaveResponseForm
import com.piikii.input.http.controller.room.dto.request.RoomSaveRequestForm
import com.piikii.input.http.controller.room.dto.request.RoomUpdateRequestForm
import com.piikii.input.http.generic.ResponseForm
import com.piikii.input.http.message.RoomMessage
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/rooms")
class RoomApi(
    private val roomUseCase: RoomUseCase,
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun saveRoom(
        @RequestBody request: RoomSaveRequestForm,
    ): ResponseForm<RoomSaveResponseForm> {
        val response = roomUseCase.save(request.toDomain())
        return ResponseForm(
            data = response,
            message = RoomMessage.SUCCESS_CREATE_ROOM,
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{roomId}")
    fun updateRoom(
        @RequestBody request: RoomUpdateRequestForm,
        @PathVariable roomId: Long,
    ): ResponseForm<Any> {
        roomUseCase.update(request.toDomain(), roomId)
        return ResponseForm(
            message = RoomMessage.SUCCESS_UPDATE_ROOM,
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{roomId}")
    fun deleteRoom(
        @PathVariable roomId: Long,
    ): ResponseForm<Any> {
        roomUseCase.delete(roomId)
        return ResponseForm(
            message = RoomMessage.SUCCESS_DELETE_ROOM,
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{roomId}")
    fun getRoom(
        @PathVariable roomId: Long,
    ): ResponseForm<RoomGetResponseForm> {
        val response = roomUseCase.retrieve(roomId)
        return ResponseForm(
            data = response,
            message = RoomMessage.SUCCESS_GET_ROOM,
        )
    }
}
