package com.piikii.input.http.controller

import com.piikii.application.port.input.CourseUseCase
import com.piikii.input.http.controller.docs.CourseApiDocs
import com.piikii.input.http.controller.dto.ResponseForm
import com.piikii.input.http.controller.dto.response.CourseExistenceResponse
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Validated
@RestController
@RequestMapping("/v1/rooms/{roomUid}/courses")
class CourseApi(
    private val courseUseCase: CourseUseCase,
) : CourseApiDocs {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/existence")
    override fun checkCourseExist(
        @NotNull @PathVariable roomUid: UUID,
    ): ResponseForm<CourseExistenceResponse> {
        return ResponseForm(
            data = CourseExistenceResponse(isExist = courseUseCase.isCourseExist(roomUid)),
        )
    }
}
