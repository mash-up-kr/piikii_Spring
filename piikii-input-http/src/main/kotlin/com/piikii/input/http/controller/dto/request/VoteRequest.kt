package com.piikii.input.http.controller.dto.request

import com.piikii.application.domain.vote.Vote
import com.piikii.application.domain.vote.VoteResult
import java.util.UUID

data class VoteRequest(
    val userId: UUID,
    val votes: List<PlaceVoteResult>,
) {
    fun toDomains(): List<Vote> {
        return this.votes.map { Vote(userId = userId, placeId = it.placeId, result = it.voteResult) }
    }
}

data class PlaceVoteResult(
    val placeId: Long,
    val voteResult: VoteResult,
)
