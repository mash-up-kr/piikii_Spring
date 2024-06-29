package com.piikii.application.port.output.persistence

import com.piikii.application.domain.roomplace.RoomPlace
import java.util.UUID

interface RoomPlaceQueryPort {
    fun retrieveByRoomPlaceId(roomPlaceId: Long): RoomPlace?

    fun retrieveAllByRoomId(roomId: UUID): List<RoomPlace>
}

interface RoomPlaceCommandPort {
    fun save(
        targetRoomId: UUID,
        roomPlace: RoomPlace,
    ): RoomPlace

    fun update(
        targetRoomPlaceId: Long,
        roomPlace: RoomPlace,
    )

    fun delete(targetRoomPlaceId: Long)
}
