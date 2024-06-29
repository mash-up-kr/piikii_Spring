package com.piikii.application.port.output.persistence

import com.piikii.application.domain.roomcategory.RoomCategory
import java.util.UUID

interface RoomCategoryQueryPort {
    fun findRoomCategoriesByRoomId(roomId: UUID): List<RoomCategory>
}

interface RoomCategoryCommandPort {
    fun saveRoomCategories(roomCategories: List<RoomCategory>)

    fun deleteRoomCategories(roomCategoryIds: List<Long>)
}
