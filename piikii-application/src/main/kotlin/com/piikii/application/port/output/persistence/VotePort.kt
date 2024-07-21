package com.piikii.application.port.output.persistence

import com.piikii.application.domain.vote.Vote

interface VoteQueryPort {
    fun findAllByPlaceIds(placeIds: List<Long>): List<Vote>
}

interface VoteCommandPort {
    fun save(votes: List<Vote>)
}
