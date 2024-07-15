package com.piikii.application.domain.room

import java.time.LocalDateTime
import java.util.UUID

data class Room(
    val roomUid: UUID,
    val name: String,
    val message: String? = null,
    val thumbnailLink: String,
    val password: Password,
    val voteDeadline: LocalDateTime?,
) {
    fun isVoteUnavailable(): Boolean {
        return this.voteDeadline == null || this.voteDeadline.isBefore(LocalDateTime.now())
    }

    fun isPasswordValid(password: Password): Boolean {
        return this.password == password
    }
}

@JvmInline
value class Password(val value: String)
