package com.piikii.application.port.input.course

import java.util.UUID

interface CourseUseCase {
    fun isCourseExist(roomId: UUID): Boolean
}
