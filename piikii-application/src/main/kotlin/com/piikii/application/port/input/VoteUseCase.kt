package com.piikii.application.port.input

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.vote.Vote
import com.piikii.application.port.input.dto.response.VoteResultResponse

interface VoteUseCase {
    fun vote(
        roomUid: UuidTypeId,
        votes: List<Vote>,
    )

    fun isVoteFinished(roomUid: UuidTypeId): Boolean

    fun getVoteResultOfRoom(roomUid: UuidTypeId): VoteResultResponse
}
