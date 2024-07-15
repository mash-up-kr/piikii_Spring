package com.piikii.application.port.output.persistence

import java.util.UUID

interface CourseQueryPort {
    fun isCourseExist(roomUid: UUID): Boolean
}

interface CourseCommandPort
