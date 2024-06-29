package com.piikii.application.port.input.course.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class CourseExistenceResponse(
    @Schema(description = "코스 생성 여부")
    val isExist: Boolean,
)
