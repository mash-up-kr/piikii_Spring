package com.piikii.application.port.input

import com.piikii.application.port.input.dto.response.CourseResponse
import java.util.UUID

interface CourseUseCase {
    fun isCourseExist(roomId: UUID): Boolean

    fun createCourse(roomId: UUID): CourseResponse
}
