package com.piikii.application.port.output.persistence

import com.piikii.application.domain.room.Room

interface RoomQueryPort {
    fun retrieve(id: Long): Room

    fun retrieveAll(ids: List<Long>): List<Room>
}

interface RoomCommandPort {
    fun save(room: Room): Room

    fun update(
        room: Room,
        id: Long,
    )

    fun delete(id: Long)

    fun updateVoteDeadline(room: Room)
}
