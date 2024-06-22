package com.piikii.application.port.output.persistence

import com.piikii.application.domain.room.Room
import java.util.UUID

interface RoomQueryPort {
    fun retrieve(roomId: UUID): Room

    fun retrieveAll(ids: List<Long>): List<Room>
}

interface RoomCommandPort {
    fun save(room: Room): Room

    fun update(
        room: Room,
        id: Long,
    )

    fun update(room: Room)

    fun delete(id: Long)

    fun updateVoteDeadline(room: Room)
}
