package com.piikii.output.persistence.postgresql.persistence.repository;

import com.piikii.output.persistence.postgresql.persistence.entity.RoomCourseResultEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoomCourseResultRepository : JpaRepository<RoomCourseResultEntity, Long> {
}
