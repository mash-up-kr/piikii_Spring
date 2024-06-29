package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.output.persistence.postgresql.persistence.entity.RoomCategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RoomCategoryRepository : JpaRepository<RoomCategoryEntity, Long> {
    fun findByRoomId(roomId: UUID): List<RoomCategoryEntity>
}
