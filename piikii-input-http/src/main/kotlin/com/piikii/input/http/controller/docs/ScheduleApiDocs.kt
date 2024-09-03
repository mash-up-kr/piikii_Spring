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
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import java.util.UUID

@Tag(name = "ScheduleApi", description = "Schedule API 입니다.")
interface ScheduleApiDocs {
    class SuccessSchedulesResponse : ResponseForm<SchedulesResponse>()

    @Operation(summary = "스케줄 등록 API", description = "스케줄을 추가/수정/삭제합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "OK success",
                content = [Content(schema = Schema(implementation = SuccessSchedulesResponse::class))],
            ),
        ],
    )
    fun registerSchedules(
        @Parameter(
            name = "roomUid",
            description = "스케줄을 등록할 방 uuid",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomUid: UUID,
        @Parameter(
            name = "request",
            description = "스케줄 등록 Request Body",
            required = true,
            schema = Schema(implementation = RegisterSchedulesRequest::class),
        )
        @Valid
        @NotNull
        @RequestBody
        request: RegisterSchedulesRequest,
    ): ResponseForm<SchedulesResponse>

    @Operation(summary = "스케줄 조회 API", description = "스케줄을 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "OK success",
                content = [Content(schema = Schema(implementation = SuccessSchedulesResponse::class))],
            ),
        ],
    )
    fun getSchedules(
        @Parameter(
            name = "roomUid",
            description = "스케줄을 조회할 방 uuid",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomUid: UUID,
    ): ResponseForm<SchedulesResponse>
}
