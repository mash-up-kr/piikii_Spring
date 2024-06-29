package com.piikii.input.http.controller

import com.piikii.application.port.input.course.CourseUseCase
import com.piikii.application.port.input.course.dto.response.CourseExistenceResponse
import com.piikii.input.http.docs.CourseApiDocs
import com.piikii.input.http.generic.ResponseForm
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/rooms/{roomId}/courses")
class CourseApi(
    private val courseUseCase: CourseUseCase,
) : CourseApiDocs {
    override fun isExistCourse(
        @PathVariable roomId: UUID,
    ): ResponseForm<CourseExistenceResponse> {
        return ResponseForm(
            data = courseUseCase.isExistCourse(roomId),
            message = SUCCESS_GET_COURSE_EXISTENCE_MESSAGE,
        )
    }

    companion object {
        const val SUCCESS_GET_COURSE_EXISTENCE_MESSAGE = "코스 생성 여부 조회 성공"
    }
}
