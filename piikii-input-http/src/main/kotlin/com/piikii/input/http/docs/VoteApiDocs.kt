package com.piikii.input.http.docs

import com.piikii.application.port.input.room.dto.request.VoteDeadlineSetRequest
import com.piikii.input.http.dto.ResponseForm
import com.piikii.input.http.dto.request.VoteRequest
import com.piikii.input.http.dto.response.VoteStatusResponse
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

@Tag(name = "VoteApi", description = "Vote (투표) API")
interface VoteApiDocs {
    @Operation(summary = "방 투표 마감일 설정 api", description = "방(Room)의 투표 마감일을 설정합니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "204", description = "UPDATED success")])
    fun changeVoteDeadline(
        @Parameter(
            name = "roomId",
            description = "투표 마감일 설정하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) roomId: UUID,
        @RequestBody(
            description = "투표 마감일 설정 요청 Body",
            required = true,
            content = [Content(schema = Schema(implementation = VoteDeadlineSetRequest::class))],
        ) request: VoteDeadlineSetRequest,
    ): ResponseForm<Unit>

    @Operation(summary = "방 투표 마감 상태조회 API", description = "투표 마감 상태를 조회합니다")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Vote status check succeed")])
    fun getVoteStatus(
        @Parameter(
            name = "roomId",
            description = "조회하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) roomId: UUID,
    ): ResponseForm<VoteStatusResponse>

    @Operation(summary = "투표하기 API", description = "투표를 진행합니다")
    @ApiResponses(value = [ApiResponse(responseCode = "201", description = "Vote succeed")])
    fun vote(
        @Parameter(
            name = "roomId",
            description = "투표하고자 하는 방 id",
            required = true,
            `in` = ParameterIn.PATH,
        ) roomId: UUID,
        @RequestBody(
            description = "투표 생성 요청 body",
            required = true,
            content = [Content(schema = Schema(implementation = VoteRequest::class))],
        ) voteRequest: VoteRequest,
    ): ResponseForm<Unit>
}
