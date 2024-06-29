package com.piikii.application.port.output.persistence

import java.util.UUID

interface CourseCommandPort {
    fun isExistCourse(roomId: UUID): Boolean
}
