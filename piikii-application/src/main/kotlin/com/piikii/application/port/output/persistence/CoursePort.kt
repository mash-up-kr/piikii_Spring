package com.piikii.application.port.output.persistence

import java.util.UUID

interface CourseQueryPort {
    fun isCourseExist(roomId: UUID): Boolean
}

interface CourseCommandPort
