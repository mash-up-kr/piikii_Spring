package com.piikii.application.port.output.persistence

import com.piikii.application.domain.generic.UuidTypeId

interface CourseQueryPort {
    fun isCourseExist(roomUid: UuidTypeId): Boolean
}

interface CourseCommandPort
