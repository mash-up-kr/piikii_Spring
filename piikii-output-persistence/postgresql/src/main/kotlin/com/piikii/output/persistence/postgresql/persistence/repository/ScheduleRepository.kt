package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.output.persistence.postgresql.persistence.entity.ScheduleEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ScheduleRepository : JpaRepository<ScheduleEntity, Long> {
    fun findByRoomIdOrderBySequenceAsc(roomId: UUID): List<ScheduleEntity>
}
