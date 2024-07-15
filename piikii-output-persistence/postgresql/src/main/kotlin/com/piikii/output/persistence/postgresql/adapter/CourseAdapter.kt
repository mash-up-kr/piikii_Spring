package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.port.output.persistence.CourseQueryPort
import com.piikii.output.persistence.postgresql.persistence.repository.CourseRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class CourseAdapter(
    private val courseRepository: CourseRepository,
) : CourseQueryPort {
    override fun isCourseExist(roomUid: UUID): Boolean {
        return courseRepository.existsByroomUid(roomUid)
    }
}
