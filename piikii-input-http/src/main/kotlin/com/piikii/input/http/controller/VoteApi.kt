package com.piikii.input.http.controller

import com.piikii.application.port.input.room.RoomUseCase
import com.piikii.application.port.input.room.dto.request.VoteDeadlineSetRequest
import com.piikii.application.port.input.vote.VoteUseCase
import com.piikii.input.http.docs.VoteApiDocs
import com.piikii.input.http.dto.ResponseForm
import com.piikii.input.http.dto.request.VoteRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/room/{roomId}/votes")
class VoteApi(
    private val voteUseCase: VoteUseCase,
    private val roomUseCase: RoomUseCase,
) : VoteApiDocs {
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/deadline")
    override fun changeVoteDeadline(
        @PathVariable roomId: UUID,
        @RequestBody request: VoteDeadlineSetRequest,
    ): ResponseForm<Unit> {
        roomUseCase.changeVoteDeadline(roomId, request.voteDeadline)
        return ResponseForm.EMPTY_RESPONSE
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    override fun vote(
        @PathVariable roomId: UUID,
        @RequestBody voteRequest: VoteRequest,
    ): ResponseForm<Unit> {
        voteUseCase.vote(roomId, voteRequest.toDomains())
        return ResponseForm.EMPTY_RESPONSE
    }
}
