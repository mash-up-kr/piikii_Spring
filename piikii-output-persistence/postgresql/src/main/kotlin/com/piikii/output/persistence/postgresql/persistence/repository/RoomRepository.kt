package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.output.persistence.postgresql.persistence.entity.RoomEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RoomRepository : JpaRepository<RoomEntity, Long> {
    fun findByRoomId(roomId: UUID): RoomEntity?
}
