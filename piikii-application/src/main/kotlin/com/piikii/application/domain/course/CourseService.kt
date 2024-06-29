package com.piikii.application.domain.course

import com.piikii.application.port.input.course.CourseUseCase
import com.piikii.application.port.input.course.dto.response.CourseExistenceResponse
import com.piikii.application.port.output.persistence.CourseQueryPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CourseService(
    private val courseQueryPort: CourseQueryPort,
) : CourseUseCase {
    override fun readCourseExistenceInRoom(roomId: UUID): CourseExistenceResponse {
        return CourseExistenceResponse(
            courseQueryPort.isExistCourse(roomId),
        )
    }
}
