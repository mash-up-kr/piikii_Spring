package com.piikii.input.http.dto.request

import com.piikii.application.domain.vote.Vote
import com.piikii.application.domain.vote.VoteResult
import java.util.UUID

data class VoteRequest(
    val userId: UUID,
    val roomId: UUID,
    val votes: List<PlaceVoteResult>,
) {
    fun toDomains(): List<Vote> {
        return this.votes.map { Vote(userId = userId, roomPlaceId = it.roomPlaceId, result = it.voteResult) }
    }
}

data class PlaceVoteResult(
    val roomPlaceId: Long,
    val voteResult: VoteResult,
)
