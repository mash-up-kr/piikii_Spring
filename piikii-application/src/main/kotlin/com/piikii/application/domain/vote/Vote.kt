package com.piikii.application.domain.vote

import java.util.UUID

data class Vote(
    val id: Long? = null,
    val userUid: UUID,
    val placeId: Long,
    val result: VoteResult,
)

enum class VoteResult {
    AGREE,
    DISAGREE,
}
