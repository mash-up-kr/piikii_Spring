package com.piikii.application.domain.room

import com.piikii.application.domain.generic.UuidTypeId
import java.time.LocalDateTime

data class Room(
    val roomUid: UuidTypeId,
    val name: String,
    val message: String? = null,
    val thumbnailLink: String,
    val password: Password,
    val voteDeadline: LocalDateTime?,
) {
    fun isVoteUnavailable(): Boolean {
        return this.voteDeadline == null || this.voteDeadline.isBefore(LocalDateTime.now())
    }

    fun isNotVoteExpired(): Boolean {
        return this.voteDeadline == null || this.voteDeadline.isAfter(LocalDateTime.now())
    }

    fun isPasswordValid(password: Password): Boolean {
        return this.password == password
    }
}

@JvmInline
value class Password(val value: String)
