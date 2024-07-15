package com.piikii.input.http.controller

import com.piikii.application.port.input.RoomUseCase
import com.piikii.application.port.input.VoteUseCase
import com.piikii.application.port.input.dto.request.VoteDeadlineSetRequest
import com.piikii.input.http.controller.docs.VoteApiDocs
import com.piikii.input.http.controller.dto.ResponseForm
import com.piikii.input.http.controller.dto.request.VoteRequest
import com.piikii.input.http.controller.dto.response.VoteStatusResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Validated
@RestController
@RequestMapping("/room/{roomUid}/votes")
class VoteApi(
    private val voteUseCase: VoteUseCase,
    private val roomUseCase: RoomUseCase,
) : VoteApiDocs {
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/deadline")
    override fun changeVoteDeadline(
        @PathVariable roomUid: UUID,
        @Valid @RequestBody request: VoteDeadlineSetRequest,
    ): ResponseForm<Unit> {
        roomUseCase.changeVoteDeadline(roomUid, request.password, request.voteDeadline)
        return ResponseForm.EMPTY_RESPONSE
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    override fun getVoteStatus(
        @PathVariable roomUid: UUID,
    ): ResponseForm<VoteStatusResponse> {
        val voteFinished = voteUseCase.isVoteFinished(roomUid)
        return ResponseForm(data = VoteStatusResponse(voteFinished))
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    override fun vote(
        @PathVariable roomUid: UUID,
        @RequestBody voteRequest: VoteRequest,
    ): ResponseForm<Unit> {
        voteUseCase.vote(roomUid, voteRequest.toDomains())
        return ResponseForm.EMPTY_RESPONSE
    }
}
