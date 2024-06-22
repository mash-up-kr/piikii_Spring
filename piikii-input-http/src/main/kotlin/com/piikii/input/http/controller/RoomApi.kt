package com.piikii.input.http.controller

import com.piikii.application.port.input.room.RoomUseCase
import com.piikii.application.port.input.room.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.room.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.room.dto.request.VoteGenerateRequestForm
import com.piikii.application.port.input.room.dto.response.RoomGetResponseForm
import com.piikii.application.port.input.room.dto.response.RoomSaveResponseForm
import com.piikii.input.http.docs.RoomApiDocs
import com.piikii.input.http.generic.ResponseForm
import com.piikii.input.http.message.RoomMessage
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/rooms")
class RoomApi(
    private val roomUseCase: RoomUseCase,
) : RoomApiDocs {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    override fun generate(
        @RequestBody request: RoomSaveRequestForm,
    ): ResponseForm<RoomSaveResponseForm> {
        val response = roomUseCase.generate(request)
        return ResponseForm(
            data = response,
            message = RoomMessage.SUCCESS_CREATE_ROOM,
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    override fun modifyInformation(
        @RequestBody request: RoomUpdateRequestForm,
    ): ResponseForm<Any> {
        roomUseCase.modify(request)
        return ResponseForm(
            message = RoomMessage.SUCCESS_UPDATE_ROOM,
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{roomId}")
    override fun remove(
        @PathVariable roomId: UUID,
    ): ResponseForm<Any> {
        roomUseCase.remove(roomId)
        return ResponseForm(
            message = RoomMessage.SUCCESS_DELETE_ROOM,
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{roomId}")
    override fun search(
        @PathVariable roomId: UUID,
    ): ResponseForm<RoomGetResponseForm> {
        val response = roomUseCase.search(roomId)
        return ResponseForm(
            data = response,
            message = RoomMessage.SUCCESS_GET_ROOM,
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/vote")
    override fun changeVoteDeadline(
        @RequestBody request: VoteGenerateRequestForm,
    ): ResponseForm<Any> {
        roomUseCase.changeVoteDeadline(request)
        return ResponseForm(
            message = RoomMessage.SUCCESS_GENERATE_VOTE,
        )
    }
}
