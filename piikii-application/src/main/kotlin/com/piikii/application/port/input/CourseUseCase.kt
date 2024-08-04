package com.piikii.application.port.input

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.port.input.dto.response.CourseResponse
import java.util.UUID

interface CourseUseCase {
    fun isCourseExist(roomUid: UuidTypeId): Boolean
    fun isCourseExist(roomUid: UUID): Boolean

    fun retrieveCourse(roomUid: UUID): CourseResponse
}
