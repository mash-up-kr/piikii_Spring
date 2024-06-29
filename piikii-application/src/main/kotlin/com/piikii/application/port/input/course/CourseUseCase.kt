package com.piikii.application.port.input.course

import com.piikii.application.port.input.course.dto.response.CourseExistenceResponse
import java.util.UUID

interface CourseUseCase {
    fun isExistCourse(roomId: UUID): CourseExistenceResponse
}
