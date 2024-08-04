package com.piikii.application.port.output.persistence

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.vote.Vote

interface VoteQueryPort {
    fun findAllByPlaceIds(placeIds: List<Long>): List<Vote>

    fun findAgreeCountByPlaceId(votes: List<Vote>): Map<Long, Int>
    fun findAllByPlaceIds(placeIds: List<LongTypeId>): List<Vote>
}

interface VoteCommandPort {
    fun save(votes: List<Vote>)
}
