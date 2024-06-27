package com.piikii.application.domain.room

import com.piikii.application.domain.generic.ThumbnailLinks
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class Room(
    val meetingName: String,
    val message: String? = null,
    val address: String,
    val meetDay: LocalDate,
    val thumbnailLinks: ThumbnailLinks,
    val password: Short,
    val voteDeadline: LocalDateTime?,
    val roomId: UUID,
) {

    fun isUnavailableToVote(): Boolean {
        return this.voteDeadline == null || this.voteDeadline.isBefore(LocalDateTime.now())
    }
}
