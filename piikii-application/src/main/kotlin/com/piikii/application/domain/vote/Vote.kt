package com.piikii.application.domain.vote

import java.util.UUID

class Vote(
    val userId: UUID,
    val roomPlaceId: Long,
    val result: VoteResult,
)
