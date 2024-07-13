package com.piikii.application.port.output.persistence

import com.piikii.application.domain.vote.Vote

interface VoteQueryPort

interface VoteCommandPort {
    fun saveVotes(votes: List<Vote>)
}
