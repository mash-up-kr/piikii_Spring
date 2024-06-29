package com.piikii.input.http.docs

import com.piikii.application.port.input.roomplace.dto.AddRoomPlaceRequest
import com.piikii.application.port.input.roomplace.dto.DeleteRoomPlaceRequest
import com.piikii.application.port.input.roomplace.dto.ModifyRoomPlaceRequest
import com.piikii.application.port.input.roomplace.dto.RoomPlaceCategoryGroupResponse
import com.piikii.application.port.input.roomplace.dto.RoomPlaceResponse
import com.piikii.input.http.generic.ResponseForm
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotNull
import java.util.UUID

@Tag(name = "RoomPlace Api", description = "방 장소 관련 API")
interface RoomPlaceDocs {
    class SuccessRoomPlaceResponse : ResponseForm<RoomPlaceResponse>()

    class SuccessRoomPlaceCategoryGroupResponse : ResponseForm<RoomPlaceCategoryGroupResponse>()

    @Operation(summary = "방 장소 추가 API", description = "방에 장소를 추가합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "CREATED success",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SuccessRoomPlaceResponse::class),
                    ),
                ],
            ),
        ],
    )
    fun addRoomPlace(
        @Parameter(
            name = "roomId",
            description = "장소를 추가하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomId: UUID,
        @RequestBody(
            description = "방 장소 생성 Request body",
            required = true,
            content = [Content(schema = Schema(implementation = AddRoomPlaceRequest::class))],
        ) addRoomPlaceRequest: AddRoomPlaceRequest,
    ): ResponseForm<RoomPlaceResponse>

    @Operation(summary = "방 장소 조회 API", description = "방에 등록된 장소를 모두 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "CREATED success",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SuccessRoomPlaceCategoryGroupResponse::class),
                    ),
                ],
            ),
        ],
    )
    fun retrieveAll(
        @Parameter(
            name = "roomId",
            description = "장소를 조회 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomId: UUID,
    ): ResponseForm<List<RoomPlaceCategoryGroupResponse>>

    @Operation(summary = "방 장소 수정 API", description = "방에 추가한 장소를 수정합니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "UPDATED success")])
    fun modifyRoomPlace(
        @Parameter(
            name = "roomId",
            description = "수정하고자 하는 장소의 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomId: UUID,
        @Parameter(
            name = "targetRoomPlaceId",
            description = "수정하고자 하는 장소의 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull targetRoomPlaceId: Long,
        modifyRoomPlaceRequest: ModifyRoomPlaceRequest,
    ): ResponseForm<Any>

    @Operation(summary = "방 장소 삭제 API", description = "방에 추가한 장소를 삭제합니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "DELETED success")])
    fun deleteRoomPlace(
        @Parameter(
            name = "roomId",
            description = "수정하고자 하는 장소의 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomId: UUID,
        @RequestBody(
            description = "방 장소 생성 Request body",
            required = true,
            content = [Content(schema = Schema(implementation = DeleteRoomPlaceRequest::class))],
        ) deleteRoomPlaceRequest: DeleteRoomPlaceRequest,
    ): ResponseForm<Any>
}
