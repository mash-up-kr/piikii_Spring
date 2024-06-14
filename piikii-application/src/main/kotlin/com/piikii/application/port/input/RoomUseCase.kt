package com.piikii.application.port.input

import com.piikii.application.domain.room.Room

interface RoomUseCase {
    fun save(request: Room): Room
    fun update(roomId: Long, request: Room)
    fun delete(roomId: Long)
    fun retrieve(roomId: Long): Room
}
