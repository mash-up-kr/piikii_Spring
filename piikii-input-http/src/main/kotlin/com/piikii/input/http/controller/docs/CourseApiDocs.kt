package com.piikii.input.http.controller.docs

import com.piikii.application.port.input.dto.response.CourseResponse
import com.piikii.input.http.controller.dto.ResponseForm
import com.piikii.input.http.controller.dto.response.CourseExistenceResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID

@Tag(name = "CourseApi", description = "Course Api 입니다.")
interface CourseApiDocs {
    @Operation(summary = "코스 생성 여부 api", description = "방 내에 코스의 생성 여부를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "OK success",
                content = [Content(schema = Schema(implementation = CourseExistenceResponse::class))],
            ),
        ],
    )
    fun checkCourseExist(
        @Parameter(
            name = "roomId",
            description = "코스 생성 여부를 조회할 방 uuid",
            required = true,
            `in` = ParameterIn.PATH,
        ) roomId: UUID,
    ): ResponseForm<CourseExistenceResponse>

    @Operation(summary = "코스 생성 api", description = "코스를 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "CREATED success",
                content = [Content(schema = Schema(implementation = CourseResponse::class))],
            ),
        ],
    )
    fun createCourse(
        @Parameter(
            name = "roomId",
            description = "코스를 생성할 방 uuid",
            required = true,
            `in` = ParameterIn.PATH,
        ) roomId: UUID,
    ): ResponseForm<CourseResponse>
}
