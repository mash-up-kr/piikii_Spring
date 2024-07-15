package com.piikii.application.port.input

import java.util.UUID

interface CourseUseCase {
    fun isCourseExist(roomUid: UUID): Boolean
}
