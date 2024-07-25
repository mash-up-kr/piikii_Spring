package com.piikii.input.http.controller

import com.piikii.application.port.input.RoomUseCase
import com.piikii.application.port.input.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.dto.response.RoomResponse
import com.piikii.application.port.input.dto.response.SaveRoomResponse
import com.piikii.input.http.controller.docs.RoomApiDocs
import com.piikii.input.http.controller.dto.ResponseForm
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Validated
@RestController
@RequestMapping("/v1/rooms")
class RoomApi(
    private val roomUseCase: RoomUseCase,
) : RoomApiDocs {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    override fun create(
        @RequestBody request: RoomSaveRequestForm,
    ): ResponseForm<SaveRoomResponse> {
        return ResponseForm(
            data = roomUseCase.create(request),
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    override fun modifyInformation(
        @RequestBody request: RoomUpdateRequestForm,
    ): ResponseForm<Unit> {
        roomUseCase.modify(request)
        return ResponseForm.EMPTY_RESPONSE
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{roomUid}")
    override fun remove(
        @NotNull @PathVariable roomUid: UUID,
    ): ResponseForm<Unit> {
        roomUseCase.remove(roomUid)
        return ResponseForm.EMPTY_RESPONSE
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{roomUid}")
    override fun search(
        @NotNull @PathVariable roomUid: UUID,
    ): ResponseForm<RoomResponse> {
        return ResponseForm(
            data = roomUseCase.findById(roomUid),
        )
    }
}
