package com.piikii.input.http.controller.room

import com.piikii.application.port.input.RoomUseCase
import com.piikii.input.http.controller.room.dto.request.RoomSaveRequestForm
import com.piikii.input.http.controller.room.dto.request.RoomUpdateRequestForm
import com.piikii.input.http.controller.room.dto.response.RoomGetResponseForm
import com.piikii.input.http.controller.room.dto.response.RoomSaveResponseForm
import com.piikii.input.http.controller.room.dto.response.toRoomGetResponse
import com.piikii.input.http.controller.room.dto.response.toRoomSaveResponse
import com.piikii.input.http.generic.ResponseForm
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/rooms")
class RoomApi(
    private val roomUseCase: RoomUseCase,
) {
    @PostMapping
    fun saveRoom(
        @RequestBody request: RoomSaveRequestForm,
    ): ResponseEntity<ResponseForm<RoomSaveResponseForm>> {
        val room = roomUseCase.save(request.toDomain())
        return ResponseEntity.created(URI("/")).body(
            ResponseForm(
                data = room.toRoomSaveResponse(),
                message = "방 생성 성공",
            ),
        )
    }

    @PutMapping("/{roomId}")
    fun updateRoom(
        @RequestBody request: RoomUpdateRequestForm,
        @PathVariable roomId: Long,
    ): ResponseEntity<ResponseForm<Any>> {
        roomUseCase.update(request.toDomain(), roomId)
        return ResponseEntity.ok(
            ResponseForm(
                message = "방 정보 수정 성공",
            ),
        )
    }

    @DeleteMapping("/{roomId}")
    fun deleteRoom(
        @PathVariable roomId: Long,
    ): ResponseEntity<ResponseForm<Any>> {
        roomUseCase.delete(roomId)
        return ResponseEntity.ok(
            ResponseForm(
                message = "방 삭제 성공",
            ),
        )
    }

    @GetMapping("/{roomId}")
    fun getRoom(
        @PathVariable roomId: Long,
    ): ResponseEntity<ResponseForm<RoomGetResponseForm>> {
        val room = roomUseCase.retrieve(roomId)
        return ResponseEntity.ok(
            ResponseForm(
                data = room.toRoomGetResponse(),
                message = "방 조회 성공",
            ),
        )
    }
}
