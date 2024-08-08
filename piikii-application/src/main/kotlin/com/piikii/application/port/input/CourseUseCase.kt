package com.piikii.application.port.input

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.port.input.dto.response.CourseResponse

interface CourseUseCase {
    fun isCourseExist(roomUid: UuidTypeId): Boolean

    fun retrieveCourse(roomUid: UuidTypeId): CourseResponse
}
