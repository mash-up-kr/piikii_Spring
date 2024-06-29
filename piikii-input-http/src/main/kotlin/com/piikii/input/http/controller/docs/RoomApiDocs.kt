package com.piikii.input.http.controller.docs

import com.piikii.application.port.input.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.dto.response.RoomResponse
import com.piikii.application.port.input.dto.response.SaveRoomResponse
import com.piikii.input.http.controller.dto.ResponseForm
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID

@Tag(name = "RoomApi", description = "방 관련 API")
interface RoomApiDocs {
    @Operation(summary = "방 생성 api", description = "방(Room)을 생성합니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "201", description = "CREATED success")])
    fun create(request: RoomSaveRequestForm): ResponseForm<SaveRoomResponse>

    @Operation(summary = "방 수정 api", description = "방(Room) 정보를 수정합니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "UPDATED success")])
    fun modifyInformation(request: RoomUpdateRequestForm): ResponseForm<Unit>

    @Operation(summary = "방 삭제 api", description = "방(Room) 정보를 삭제합니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "DELETED success")])
    fun remove(
        @Parameter(
            name = "roomId",
            description = "삭제하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) roomId: UUID,
    ): ResponseForm<Unit>

    @Operation(summary = "방 조회 api", description = "방(Room) 정보를 조회합니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "GET success")])
    fun search(
        @Parameter(
            name = "roomId",
            description = "삭제하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) roomId: UUID,
    ): ResponseForm<RoomResponse>
}
