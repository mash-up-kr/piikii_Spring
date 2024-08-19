package com.piikii.input.http.controller.docs

import com.piikii.application.port.input.dto.request.RoomPasswordVerifyRequestForm
import com.piikii.application.port.input.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.dto.response.RoomResponse
import com.piikii.application.port.input.dto.response.SaveRoomResponse
import com.piikii.input.http.controller.dto.ResponseForm
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

@Tag(name = "RoomApi", description = "방 관련 API")
interface RoomApiDocs {
    class SuccessSaveRoomResponse : ResponseForm<SaveRoomResponse>()

    class SuccessRoomResponse : ResponseForm<RoomResponse>()

    @Operation(summary = "방 생성 API", description = "방(Room)을 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "CREATED success",
                content = [Content(schema = Schema(implementation = SuccessSaveRoomResponse::class))],
            ),
        ],
    )
    fun create(
        @Parameter(
            description = "방 생성 요청 정보",
            required = true,
            schema = Schema(implementation = RoomSaveRequestForm::class),
        )
        @Valid
        @NotNull
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        request: RoomSaveRequestForm,
    ): ResponseForm<SaveRoomResponse>

    @Operation(summary = "방 수정 API", description = "방(Room) 정보를 수정합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "UPDATED success",
                content = [Content(schema = Schema(implementation = SuccessRoomResponse::class))],
            ),
        ],
    )
    fun modifyInformation(
        @Parameter(
            description = "방 수정 요청 정보",
            required = true,
            schema = Schema(implementation = RoomUpdateRequestForm::class),
        )
        @Valid
        @NotNull
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        request: RoomUpdateRequestForm,
    ): ResponseForm<Unit>

    @Operation(summary = "방 삭제 API", description = "방(Room) 정보를 삭제합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "DELETED success",
                content = [Content(schema = Schema(implementation = ResponseForm::class))],
            ),
        ],
    )
    fun remove(
        @Parameter(
            name = "roomUid",
            description = "삭제하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomUid: UUID,
    ): ResponseForm<Unit>

    @Operation(summary = "방 조회 API", description = "방(Room) 정보를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "GET success",
                content = [Content(schema = Schema(implementation = SuccessRoomResponse::class))],
            ),
        ],
    )
    fun search(
        @Parameter(
            name = "roomUid",
            description = "조회하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomUid: UUID,
    ): ResponseForm<RoomResponse>

    @Operation(summary = "방 비밀번호 검증 API", description = "방(Room) 비밀번호를 검증합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "GET success",
                content = [Content(schema = Schema(implementation = ResponseForm::class))],
            ),
        ],
    )
    fun verifyPassword(
        @Parameter(
            name = "roomUid",
            description = "검증하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomUid: UUID,
        @Valid @NotNull @RequestBody request: RoomPasswordVerifyRequestForm,
    ): ResponseForm<Unit>
}
