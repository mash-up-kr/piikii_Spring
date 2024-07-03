package com.piikii.input.http.controller

import com.piikii.application.port.input.PlaceUseCase
import com.piikii.application.port.input.dto.request.AddPlaceRequest
import com.piikii.application.port.input.dto.request.DeletePlaceRequest
import com.piikii.application.port.input.dto.request.ModifyPlaceRequest
import com.piikii.application.port.input.dto.response.PlaceResponse
import com.piikii.application.port.input.dto.response.PlaceTypeGroupResponse
import com.piikii.input.http.controller.docs.PlaceDocs
import com.piikii.input.http.controller.dto.ResponseForm
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
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

@Validated
@RestController
@RequestMapping("/v1/rooms/{roomId}/places")
class PlaceApi(
    private val placeUseCase: PlaceUseCase,
) : PlaceDocs {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    override fun addPlace(
        @PathVariable roomId: UUID,
        @RequestBody @Valid addPlaceRequest: AddPlaceRequest,
    ): ResponseForm<PlaceResponse> {
        return ResponseForm(
            data = placeUseCase.addPlace(roomId, addPlaceRequest),
        )
    }

    @GetMapping
    override fun retrieveAll(
        @PathVariable roomId: UUID,
    ): ResponseForm<List<PlaceTypeGroupResponse>> {
        return ResponseForm(
            data = placeUseCase.findAllByRoomId(roomId),
        )
    }

    @PatchMapping("/{targetPlaceId}")
    override fun modifyPlace(
        @PathVariable roomId: UUID,
        @PathVariable targetPlaceId: Long,
        @RequestBody @Valid modifyPlaceRequest: ModifyPlaceRequest,
    ): ResponseForm<Unit> {
        return ResponseForm(
            data = placeUseCase.modify(roomId, targetPlaceId, modifyPlaceRequest),
        )
    }

    @DeleteMapping
    override fun deletePlace(
        @PathVariable roomId: UUID,
        @RequestBody @Valid deletePlaceRequest: DeletePlaceRequest,
    ): ResponseForm<Unit> {
        return ResponseForm(
            data = placeUseCase.delete(deletePlaceRequest.targetPlaceId),
        )
    }
}
