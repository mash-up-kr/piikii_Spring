package com.piikii.application.domain.fixture

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.vote.Vote
import com.piikii.application.domain.vote.VoteResult
import java.util.UUID

class VoteFixture(
    private var id: LongTypeId = LongTypeId(0L),
    private var userUid: UuidTypeId = UuidTypeId(UUID.randomUUID()),
    private var schedulePlaceId: LongTypeId = LongTypeId(0L),
    private var result: VoteResult = VoteResult.DISAGREE,
) {
    fun placeId(placeId: LongTypeId): VoteFixture {
        this.schedulePlaceId = placeId
        return this
    }

    fun result(result: VoteResult): VoteFixture {
        this.result = result
        return this
    }

    fun build(): Vote {
        return Vote(
            id = this.id,
            userUid = this.userUid,
            schedulePlaceId = this.schedulePlaceId,
            result = this.result,
        )
    }

    companion object {
        fun create() = VoteFixture()
    }
}
