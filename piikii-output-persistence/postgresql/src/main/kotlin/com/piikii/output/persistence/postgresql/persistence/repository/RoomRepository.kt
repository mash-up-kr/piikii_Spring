package com.piikii.output.persistence.postgresql.persistence.repository;

import com.piikii.output.persistence.postgresql.persistence.entity.RoomEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository : JpaRepository<RoomEntity, Long> {
}
