package com.piikii.application.port.input

import com.piikii.application.domain.vote.Vote
import java.util.UUID

interface VoteUseCase {
    fun vote(
        roomId: UUID,
        votes: List<Vote>,
    )

    fun isVoteFinished(roomId: UUID): Boolean
}
