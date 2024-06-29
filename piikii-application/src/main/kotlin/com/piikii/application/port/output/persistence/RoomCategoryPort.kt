package com.piikii.application.port.output.persistence

import com.piikii.application.domain.roomcategory.RoomCategory
import com.piikii.application.domain.roomplace.RoomPlace
import java.util.UUID

interface RoomCategoryQueryPort {
    fun retrieveById(id: Long): RoomCategory

    fun retrieveAllByRoomId(roomId: UUID): List<RoomCategory>
}

interface RoomCategoryCommandPort {
    fun save(
        roomId: UUID,
        roomPlace: RoomPlace,
    ): RoomPlace

    fun update(
        roomId: UUID,
        roomPlace: RoomPlace,
    )

    fun delete(
        roomId: UUID,
        targetRoomPlaceId: Long,
    )
}
