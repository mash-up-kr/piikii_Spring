package com.piikii.input.http.controller

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.port.input.CourseUseCase
import com.piikii.application.port.input.dto.response.CourseResponse
import com.piikii.input.http.controller.docs.CourseApiDocs
import com.piikii.input.http.controller.dto.ResponseForm
import com.piikii.input.http.controller.dto.response.CourseExistenceResponse
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Validated
@RestController
@RequestMapping("/v1/rooms/{roomUid}/course")
class CourseApi(
    private val courseUseCase: CourseUseCase,
) : CourseApiDocs {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/existence")
    override fun checkCourseExist(
        @NotNull @PathVariable roomUid: UUID,
    ): ResponseForm<CourseExistenceResponse> {
        return ResponseForm(
            data = CourseExistenceResponse(isExist = courseUseCase.isCourseExist(UuidTypeId(roomUid))),
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    override fun retrieveCourse(
        @NotNull @PathVariable roomUid: UUID,
    ): ResponseForm<CourseResponse> {
        return ResponseForm(
            data = courseUseCase.retrieveCourse(UuidTypeId(roomUid)),
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/place/{placeId}")
    override fun updateCoursePlace(
        @NotNull @PathVariable roomUid: UUID,
        @NotNull @PathVariable placeId: Long,
    ): ResponseForm<Unit> {
        courseUseCase.updateCoursePlace(UuidTypeId(roomUid), LongTypeId(placeId))
        return ResponseForm.EMPTY_RESPONSE
    }
}
