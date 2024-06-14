package com.piikii.input.http.controller

import com.piikii.application.domain.room.Room
import com.piikii.application.port.input.RoomUseCase
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
    private val roomUseCase: RoomUseCase
) { //TODO: DTO 추가

    @PostMapping
    fun saveRoom(@RequestBody room: Room): ResponseEntity<ResponseForm<Room>> {
        val response = roomUseCase.save(room)
        return ResponseEntity
            .created(URI("/${response.address}"))
            .body(ResponseForm(response))
    }

    @PutMapping("/{roomId}")
    fun updateRoom(@RequestBody request: Room, @PathVariable roomId: Long): ResponseEntity<ResponseForm<Room>> {
        roomUseCase.update(request, roomId)
        return ResponseEntity.ok(ResponseForm(request))
    }

    @DeleteMapping("/{roomId}")
    fun deleteRoom(@PathVariable roomId: Long): ResponseEntity<ResponseForm<Long>> {
        roomUseCase.delete(roomId)
        return ResponseEntity.ok(ResponseForm(roomId))
    }

    @GetMapping("/{roomId}")
    fun getRoom(@PathVariable roomId: Long): ResponseEntity<ResponseForm<Room>> {
        val response = roomUseCase.retrieve(roomId)
        return ResponseEntity.ok(ResponseForm(response))
    }
}
