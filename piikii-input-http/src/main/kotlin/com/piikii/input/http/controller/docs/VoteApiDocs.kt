package com.piikii.input.http.controller.docs

import com.piikii.application.port.input.dto.request.VoteDeadlineSetRequest
import com.piikii.application.port.input.dto.request.VoteSaveRequest
import com.piikii.application.port.input.dto.response.VoteResultResponse
import com.piikii.application.port.input.dto.response.VoteStatusResponse
import com.piikii.application.port.input.dto.response.VotedPlacesResponse
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
import java.util.UUID

@Tag(name = "VoteApi", description = "Vote (투표) API")
interface VoteApiDocs {
    class SuccessVoteStatusResponse : ResponseForm<VoteStatusResponse>()

    class SuccessVoteResultResponse : ResponseForm<VoteResultResponse>()

    class SuccessVotedPlacesResponse : ResponseForm<VotedPlacesResponse>()

    @Operation(summary = "방 투표 마감일 설정 API", description = "방(Room)의 투표 마감일을 설정합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "UPDATED success",
                content = [Content(schema = Schema(implementation = ResponseForm::class))],
            ),
        ],
    )
    fun changeVoteDeadline(
        @Parameter(
            name = "roomUid",
            description = "투표 마감일 설정하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomUid: UUID,
        @Parameter(
            name = "request",
            description = "투표 마감일 설정 요청 Body",
            required = true,
            schema = Schema(implementation = VoteDeadlineSetRequest::class),
        )
        @Valid
        @NotNull
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        request: VoteDeadlineSetRequest,
    ): ResponseForm<Unit>

    @Operation(summary = "방 투표 마감 상태조회 API", description = "투표 마감 상태를 조회합니다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Vote status check succeed",
                content = [Content(schema = Schema(implementation = SuccessVoteStatusResponse::class))],
            ),
        ],
    )
    fun getVoteStatus(
        @Parameter(
            name = "roomUid",
            description = "조회하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomUid: UUID,
    ): ResponseForm<VoteStatusResponse>

    @Operation(summary = "투표하기 API", description = "투표를 진행합니다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Vote succeed",
                content = [Content(schema = Schema(implementation = ResponseForm::class))],
            ),
        ],
    )
    fun vote(
        @Parameter(
            name = "roomUid",
            description = "투표하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomUid: UUID,
        @Parameter(
            name = "voteSaveRequest",
            description = "투표 생성 요청 body",
            required = true,
            schema = Schema(implementation = VoteSaveRequest::class),
        )
        @Valid
        @NotNull
        @io.swagger.v3.oas.annotations.parameters.RequestBody
        voteSaveRequest: VoteSaveRequest,
    ): ResponseForm<Unit>

    @Operation(summary = "투표 결과 조회 API", description = "투표 결과를 조회합니다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Vote result",
                content = [Content(schema = Schema(implementation = SuccessVoteResultResponse::class))],
            ),
        ],
    )
    fun getVoteResultOfRoom(
        @Parameter(
            name = "roomUid",
            description = "조회하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomUid: UUID,
    ): ResponseForm<VoteResultResponse>

    @Operation(summary = "투표한 장소 조회 API", description = "유저가 투표한 장소 목록을 조회합니다")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Vote result",
                content = [Content(schema = Schema(implementation = SuccessVotedPlacesResponse::class))],
            ),
        ],
    )
    fun retrieveVotedPlaceByUser(
        @Parameter(
            name = "roomUid",
            description = "조회하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull roomUid: UUID,
        @Parameter(
            name = "userUid",
            description = "조회하고자 하는 유저 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) @NotNull userUid: UUID,
    ): ResponseForm<VotedPlacesResponse>
}
