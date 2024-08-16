package com.piikii.application.port.output.persistence

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.vote.Vote

interface VoteQueryPort {
    fun findAgreeCountByPlaceId(votes: List<Vote>): Map<Long, Int>

    fun findAllByPlaceIds(placeIds: List<LongTypeId>): List<Vote>

    fun findAllByUserUid(userUid: UuidTypeId): List<Vote>
}

interface VoteCommandPort {
    fun save(votes: List<Vote>)
}
