package com.piikii.application.domain.vote

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId

data class Vote(
    val id: LongTypeId,
    val userUid: UuidTypeId,
    val placeId: LongTypeId,
    val result: VoteResult,
)

enum class VoteResult {
    AGREE,
    DISAGREE,
}
