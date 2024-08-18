package com.piikii.application.port.output.persistence

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.room.Password
import com.piikii.application.domain.room.Room

interface RoomQueryPort {
    fun findById(roomUid: UuidTypeId): Room

    fun verifyPassword(
        room: Room,
        password: Password,
    )
}

interface RoomCommandPort {
    fun save(room: Room): Room

    fun update(room: Room)

    fun delete(roomUid: UuidTypeId)
}
