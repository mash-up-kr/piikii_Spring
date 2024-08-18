package com.piikii.application.port.input

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.port.input.dto.request.CourseRequest
import com.piikii.application.port.input.dto.response.CourseResponse

interface CourseUseCase {
    fun isCourseExist(roomUid: UuidTypeId): Boolean

    fun retrieveCourse(roomUid: UuidTypeId): CourseResponse

    fun updateCoursePlace(
        roomUid: UuidTypeId,
        placeId: LongTypeId,
        courseUpdateRequest: CourseRequest,
    )
}
