package com.piikii.input.http.docs

import com.piikii.input.http.dto.ResponseForm
import com.piikii.input.http.dto.request.VoteRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "VoteApi", description = "Vote (투표) API")
interface VoteApiDocs {
    @Operation(summary = "투표 API", description = "투표를 진행합니다")
    @ApiResponses(value = [ApiResponse(responseCode = "201", description = "Vote succeed")])
    fun vote(
        @RequestBody(
            description = "투표 생성 Request body",
            required = true,
            content = [Content(schema = Schema(implementation = VoteRequest::class))],
        ) voteRequest: VoteRequest,
    ): ResponseForm<Unit>
}
