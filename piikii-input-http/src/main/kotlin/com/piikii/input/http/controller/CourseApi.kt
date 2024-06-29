package com.piikii.input.http.controller

import com.piikii.application.port.input.CourseUseCase
import com.piikii.application.port.input.dto.response.CourseResponse
import com.piikii.input.http.controller.docs.CourseApiDocs
import com.piikii.input.http.controller.dto.ResponseForm
import com.piikii.input.http.controller.dto.response.CourseExistenceResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
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
    override fun checkCourseExist(
        @PathVariable roomId: UUID,
    ): ResponseForm<CourseExistenceResponse> {
        return ResponseForm(
            data = CourseExistenceResponse(isExist = courseUseCase.isCourseExist(roomId)),
        )
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    override fun createCourse(
        @PathVariable roomId: UUID,
    ): ResponseForm<CourseResponse> {
        return ResponseForm(
            data = courseUseCase.createCourse(roomId),
        )
    }
}
