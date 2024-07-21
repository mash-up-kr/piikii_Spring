package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.vote.Vote
import com.piikii.application.domain.vote.VoteResult
import java.util.UUID

data class VoteSaveRequest(
    val userUid: UUID,
    val votes: List<PlaceVoteResult>,
) {
    fun toDomains(): List<Vote> {
        return this.votes
            .map {
                Vote(
                    userUid = userUid,
                    placeId = it.placeId,
                    result = it.voteResult,
                )
            }
    }
}

data class PlaceVoteResult(
    val placeId: Long,
    val voteResult: VoteResult,
)
