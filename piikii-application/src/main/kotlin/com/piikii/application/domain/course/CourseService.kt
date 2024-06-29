package com.piikii.application.domain.course

import com.piikii.application.port.input.course.CourseUseCase
import com.piikii.application.port.input.course.dto.response.CourseExistenceResponse
import com.piikii.application.port.output.persistence.CourseCommandPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CourseService(
    private val courseCommandPort: CourseCommandPort,
) : CourseUseCase {
    override fun isExistCourse(roomId: UUID): CourseExistenceResponse {
        return CourseExistenceResponse(
            courseCommandPort.isExistCourse(roomId),
        )
    }
}
