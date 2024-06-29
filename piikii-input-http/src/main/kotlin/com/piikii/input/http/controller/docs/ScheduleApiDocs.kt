package com.piikii.input.http.controller.docs

import com.piikii.application.port.input.dto.request.CreateSchedulesRequest
import com.piikii.application.port.input.dto.request.DeleteSchedulesRequest
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
    @Operation(summary = "방 스케줄 생성 api", description = "방 스케줄를 생성합니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "201", description = "CREATED success")])
    fun createSchedules(
        @Parameter(
            name = "roomId",
            description = "스케줄를 생성할 방 uuid",
            required = true,
            `in` = ParameterIn.PATH,
        ) roomId: UUID,
        @RequestBody(
            description = "스케줄 생성 Request Body",
            required = true,
            content = [Content(schema = Schema(implementation = CreateSchedulesRequest::class))],
        ) request: CreateSchedulesRequest,
    ): ResponseForm<Unit>

    @Operation(summary = "방 스케줄 조회 api", description = "방 스케줄를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "OK success",
                content = [Content(schema = Schema(implementation = SchedulesResponse::class))],
            ),
        ],
    )
    fun getSchedules(
        @Parameter(
            name = "roomId",
            description = "스케줄를 조회할 방 uuid",
            required = true,
            `in` = ParameterIn.PATH,
        ) roomId: UUID,
    ): ResponseForm<SchedulesResponse>

    @Operation(summary = "방 스케줄 삭제 api", description = "방 스케줄를 삭제합니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "204", description = "NO_CONTENT success")])
    fun deleteSchedules(
        @Parameter(
            name = "roomId",
            description = "스케줄를 삭제할 방 uuid",
            required = true,
            `in` = ParameterIn.PATH,
        ) roomId: UUID,
        @RequestBody(
            description = "스케줄 삭제 Request Body",
            required = true,
            content = [Content(schema = Schema(implementation = DeleteSchedulesRequest::class))],
        ) request: DeleteSchedulesRequest,
    ): ResponseForm<Unit>
}
