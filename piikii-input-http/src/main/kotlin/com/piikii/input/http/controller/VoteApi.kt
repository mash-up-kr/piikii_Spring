package com.piikii.input.http.controller

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.port.input.RoomUseCase
import com.piikii.application.port.input.VoteUseCase
import com.piikii.application.port.input.dto.request.VoteDeadlineSetRequest
import com.piikii.application.port.input.dto.request.VoteSaveRequest
import com.piikii.application.port.input.dto.response.VoteResultResponse
import com.piikii.application.port.input.dto.response.VoteStatusResponse
import com.piikii.application.port.input.dto.response.VotedPlacesResponse
import com.piikii.input.http.aspect.PreventDuplicateRequest
import com.piikii.input.http.controller.docs.VoteApiDocs
import com.piikii.input.http.controller.dto.ResponseForm
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
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
@RequestMapping("/v1/rooms/{roomUid}/votes")
class VoteApi(
    private val voteUseCase: VoteUseCase,
    private val roomUseCase: RoomUseCase,
) : VoteApiDocs {
    @PreventDuplicateRequest("#roomUid")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/deadline")
    override fun changeVoteDeadline(
        @NotNull @PathVariable roomUid: UUID,
        @Valid @NotNull @RequestBody request: VoteDeadlineSetRequest,
    ): ResponseForm<Unit> {
        roomUseCase.verifyPassword(UuidTypeId(roomUid), request.password)
        roomUseCase.changeVoteDeadline(UuidTypeId(roomUid), request.voteDeadline)
        return ResponseForm.EMPTY_RESPONSE
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/status")
    override fun getVoteStatus(
        @NotNull @PathVariable roomUid: UUID,
    ): ResponseForm<VoteStatusResponse> {
        val voteFinished = voteUseCase.isVoteFinished(UuidTypeId(roomUid))
        return ResponseForm(data = VoteStatusResponse(voteFinished))
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    override fun vote(
        @NotNull @PathVariable roomUid: UUID,
        @Valid @NotNull @RequestBody voteSaveRequest: VoteSaveRequest,
    ): ResponseForm<Unit> {
        voteUseCase.vote(UuidTypeId(roomUid), voteSaveRequest.toDomains())
        return ResponseForm.EMPTY_RESPONSE
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    override fun getVoteResultOfRoom(
        @NotNull @PathVariable roomUid: UUID,
    ): ResponseForm<VoteResultResponse> {
        val voteResult = voteUseCase.getVoteResultOfRoom(UuidTypeId(roomUid))
        return ResponseForm(data = voteResult)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user/{userUid}")
    override fun retrieveVotedPlaceByUser(
        @NotNull @PathVariable roomUid: UUID,
        @NotNull @PathVariable userUid: UUID,
    ): ResponseForm<VotedPlacesResponse> {
        val votedResult = voteUseCase.retrieveVotedPlaceByUser(UuidTypeId(roomUid), UuidTypeId(userUid))
        return ResponseForm(data = votedResult)
    }
}
