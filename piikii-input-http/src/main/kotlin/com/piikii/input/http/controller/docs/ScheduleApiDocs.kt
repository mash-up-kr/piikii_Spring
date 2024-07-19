package com.piikii.input.http.controller.docs

import com.piikii.application.port.input.dto.request.RegisterSchedulesRequest
import com.piikii.application.port.input.dto.response.SchedulesResponse
import com.piikii.input.http.controller.dto.ResponseForm
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID

@Tag(name = "ScheduleApi", description = "Schedule Api 입니다.")
interface ScheduleApiDocs {
    @Operation(summary = "스케줄 등록", description = "스케줄을 추가/수정/삭제합니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK success")])
    fun registerSchedules(
        @Parameter(
            name = "roomUid",
            description = "스케줄를 등록할 방 uuid",
            required = true,
            `in` = ParameterIn.PATH,
        ) roomUid: UUID,
        @RequestBody(
            description = "스케줄 등록 Request Body",
            required = true,
            content = [Content(schema = Schema(implementation = RegisterSchedulesRequest::class))],
        ) request: RegisterSchedulesRequest,
    ): ResponseForm<Unit>

    @Operation(summary = "스케줄 조회", description = "스케줄을 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "OK success",
                content = [Content(schema = Schema(implementation = SchedulesResponse::class))]
            ),
        ],
    )
    fun getSchedules(
        @Parameter(
            name = "roomUid",
            description = "스케줄를 등록할 방 uuid",
            required = true,
            `in` = ParameterIn.PATH,
        ) roomUid: UUID,
    ): ResponseForm<SchedulesResponse>
}
