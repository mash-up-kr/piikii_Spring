package com.piikii.output.persistence.postgresql.persistence.repository;

import com.piikii.output.persistence.postgresql.persistence.entity.RoomPlaceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoomPlaceRepository : JpaRepository<RoomPlaceEntity, Long> {
}
