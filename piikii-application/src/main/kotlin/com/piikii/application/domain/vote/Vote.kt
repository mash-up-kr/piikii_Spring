package com.piikii.application.domain.vote

import java.util.UUID

data class Vote(
    val userId: UUID,
    val placeId: Long,
    val result: VoteResult,
)
