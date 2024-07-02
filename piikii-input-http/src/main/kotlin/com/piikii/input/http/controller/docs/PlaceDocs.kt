package com.piikii.input.http.controller.docs

import com.piikii.application.port.input.dto.request.AddPlaceRequest
import com.piikii.application.port.input.dto.request.DeletePlaceRequest
import com.piikii.application.port.input.dto.request.ModifyPlaceRequest
import com.piikii.application.port.input.dto.response.PlaceResponse
import com.piikii.application.port.input.dto.response.PlaceTypeGroupResponse
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
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Tag(name = "Place Api", description = "방 장소 관련 API")
interface PlaceDocs {
    class SuccessPlaceResponse : ResponseForm<PlaceResponse>()

    class SuccessPlaceTypeGroupResponse : ResponseForm<PlaceTypeGroupResponse>()

    @Operation(summary = "방 장소 추가 API", description = "방에 장소를 추가합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "추가 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SuccessPlaceResponse::class),
                    ),
                ],
            ),
        ],
    )
    fun addPlace(
        @Parameter(
            name = "roomId",
            description = "장소를 추가하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomId: UUID,
        @RequestBody(
            description = "방 장소 생성 Request body",
            required = true,
            content = [Content(schema = Schema(implementation = AddPlaceRequest::class))],
        ) @Valid addPlaceRequest: AddPlaceRequest,
        multipartFile: MultipartFile,
    ): ResponseForm<PlaceResponse>

    @Operation(summary = "방 장소 조회 API", description = "방에 등록된 장소를 모두 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "조회 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SuccessPlaceTypeGroupResponse::class),
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
    ): ResponseForm<List<PlaceTypeGroupResponse>>

    @Operation(summary = "방 장소 수정 API", description = "방에 추가한 장소를 수정합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "수정 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SuccessPlaceResponse::class),
                    ),
                ],
            ),
        ],
    )
    fun modifyPlace(
        @Parameter(
            name = "roomId",
            description = "수정하고자 하는 장소의 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomId: UUID,
        @Parameter(
            name = "targetPlaceId",
            description = "수정하고자 하는 장소의 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull targetPlaceId: Long,
        @Valid modifyPlaceRequest: ModifyPlaceRequest,
    ): ResponseForm<PlaceResponse>

    @Operation(summary = "방 장소 삭제 API", description = "방에 추가한 장소를 삭제합니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "삭제 성공")])
    fun deletePlace(
        @Parameter(
            name = "roomId",
            description = "수정하고자 하는 장소의 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomId: UUID,
        @RequestBody(
            description = "방 장소 생성 Request body",
            required = true,
            content = [Content(schema = Schema(implementation = DeletePlaceRequest::class))],
        ) @Valid deletePlaceRequest: DeletePlaceRequest,
    ): ResponseForm<Unit>
}
