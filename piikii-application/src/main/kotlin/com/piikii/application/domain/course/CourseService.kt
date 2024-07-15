package com.piikii.application.domain.course

import com.piikii.application.port.input.CourseUseCase
import com.piikii.application.port.output.persistence.CourseQueryPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CourseService(
    private val courseQueryPort: CourseQueryPort,
) : CourseUseCase {
    override fun isCourseExist(roomUid: UUID): Boolean {
        return courseQueryPort.isCourseExist(roomUid)
    }
}
