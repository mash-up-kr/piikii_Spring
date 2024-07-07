package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.output.persistence.postgresql.persistence.entity.PlaceEntity
import com.piikii.output.persistence.postgresql.persistence.repository.projection.PlaceScheduleProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface PlaceRepository : JpaRepository<PlaceEntity, Long> {
    fun findAllByRoomId(roomId: UUID): List<PlaceEntity>

    @Query(
        "SELECT p as place, s as schedule " +
            "FROM PlaceEntity p " +
            "JOIN FETCH ScheduleEntity s ON p.scheduleId = s.id WHERE p.roomId = :roomId",
    )
    fun findAllWithSchedulesByRoomId(roomId: UUID): List<PlaceScheduleProjection>
}
