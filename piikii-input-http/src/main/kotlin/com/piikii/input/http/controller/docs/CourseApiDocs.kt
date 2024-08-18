package com.piikii.input.http.controller.docs

import com.piikii.application.port.input.dto.request.CourseRequest
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
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID

@Tag(name = "CourseApi", description = "Course API 입니다.")
interface CourseApiDocs {
    class SuccessCourseExistenceResponse : ResponseForm<CourseExistenceResponse>()

    class SuccessCourseResponse : ResponseForm<CourseResponse>()

    @Operation(summary = "코스 생성 여부 API", description = "방 내에 코스의 생성 여부를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "OK success",
                content = [Content(schema = Schema(implementation = SuccessCourseExistenceResponse::class))],
            ),
        ],
    )
    fun checkCourseExist(
        @Parameter(
            name = "roomUid",
            description = "코스 생성 여부를 조회할 방 uuid",
            required = true,
            `in` = ParameterIn.PATH,
        )
        @NotNull roomUid: UUID,
    ): ResponseForm<CourseExistenceResponse>

    @Operation(summary = "코스 조회 API", description = "방 내에 코스 정보를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "OK success",
                content = [Content(schema = Schema(implementation = SuccessCourseResponse::class))],
            ),
        ],
    )
    fun retrieveCourse(
        @Parameter(
            name = "roomUid",
            description = "코스를 조회할 방 uuid",
            required = true,
            `in` = ParameterIn.PATH,
        )
        @NotNull roomUid: UUID,
    ): ResponseForm<CourseResponse>

    @Operation(summary = "코스 장소 수정 API", description = "코스 대상 장소를 수정합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "OK success",
                content = [Content(schema = Schema(implementation = ResponseForm::class))],
            ),
        ],
    )
    fun updateCoursePlace(
        @Parameter(
            name = "roomUid",
            description = "코스를 수정할 방 uuid",
            required = true,
            `in` = ParameterIn.PATH,
        )
        @NotNull roomUid: UUID,
        @Parameter(
            name = "placeId",
            description = "코스 장소로 선정할 장소 id",
            required = true,
            `in` = ParameterIn.PATH,
        )
        @NotNull placeId: Long,
        @Valid @NotNull @RequestBody courseUpdateRequest: CourseRequest,
    ): ResponseForm<Unit>
}
