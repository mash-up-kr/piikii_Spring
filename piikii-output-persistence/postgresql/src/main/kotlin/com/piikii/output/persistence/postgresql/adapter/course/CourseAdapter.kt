package com.piikii.output.persistence.postgresql.adapter.course

import com.piikii.application.port.output.persistence.CourseCommandPort
import com.piikii.output.persistence.postgresql.persistence.repository.CourseRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class CourseAdapter(
    private val courseRepository: CourseRepository,
) : CourseCommandPort {
    override fun isExistCourse(roomId: UUID): Boolean {
        return courseRepository.existsByRoomId(roomId)
    }
}
