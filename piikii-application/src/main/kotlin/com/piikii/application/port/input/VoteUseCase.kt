package com.piikii.application.port.input

import com.piikii.application.domain.vote.Vote
import com.piikii.application.port.input.dto.response.VoteResultResponse
import java.util.UUID

interface VoteUseCase {
    fun vote(
        roomUid: UUID,
        votes: List<Vote>,
    )

    fun isVoteFinished(roomUid: UUID): Boolean

    fun getVoteResultOfRoom(roomUid: UUID): VoteResultResponse
}
