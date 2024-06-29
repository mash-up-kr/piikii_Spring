package com.piikii.application.domain.room

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class Room(
    val meetingName: String,
    val message: String? = null,
    val address: String,
    val meetDay: LocalDate,
    val thumbnailLink: String,
    val password: Password,
    val voteDeadline: LocalDateTime?,
    val roomId: UUID,
    val isCourseCreated: Boolean,
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
