package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.output.persistence.postgresql.persistence.entity.CourseEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CourseRepository : JpaRepository<CourseEntity, Long> {
    fun existsByroomUid(roomUid: UUID): Boolean
}
