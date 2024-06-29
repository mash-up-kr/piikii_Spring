package com.piikii.input.http.controller

import com.piikii.application.port.input.vote.VoteUseCase
import com.piikii.input.http.docs.VoteApiDocs
import com.piikii.input.http.dto.ResponseForm
import com.piikii.input.http.dto.request.VoteRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/room/votes")
class VoteApi(private val voteUseCase: VoteUseCase) : VoteApiDocs {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    override fun vote(
        @RequestBody voteRequest: VoteRequest,
    ): ResponseForm<Unit> {
        voteUseCase.vote(voteRequest.roomId, voteRequest.toDomain())
        return ResponseForm.EMPTY_RESPONSE
    }
}
