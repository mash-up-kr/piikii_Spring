package com.piikii.output.persistence.postgresql.persistence.repository;

import com.piikii.output.persistence.postgresql.persistence.entity.RoomCategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoomCategoryRepository : JpaRepository<RoomCategoryEntity, Long> {
}
