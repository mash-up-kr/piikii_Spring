package com.piikii.application.domain.roomvote

import java.util.*

class RoomVote(
    val userId: UUID,
    val roomPlaceId: Long,
    val content: String
) {
}
