package com.piikii.input.http.controller

import com.piikii.application.port.input.course.CourseUseCase
import com.piikii.input.http.docs.CourseApiDocs
import com.piikii.input.http.dto.ResponseForm
import com.piikii.input.http.dto.response.CourseExistenceResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/rooms/{roomId}/courses")
class CourseApi(
    private val courseUseCase: CourseUseCase,
) : CourseApiDocs {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/existence")
    override fun readCourseExistenceInRoom(
        @PathVariable roomId: UUID,
    ): ResponseForm<CourseExistenceResponse> {
        return ResponseForm(
            data = CourseExistenceResponse(courseUseCase.isCourseExist(roomId)),
            message = SUCCESS_GET_COURSE_EXISTENCE_MESSAGE,
        )
    }

    companion object {
        const val SUCCESS_GET_COURSE_EXISTENCE_MESSAGE = "코스 생성 여부 조회 성공"
    }
}
