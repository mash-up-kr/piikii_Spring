package com.piikii.application.domain.room

import com.piikii.application.domain.generic.ThumbnailLinks
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class Room(
    val meetingName: String,
    val message: String? = null,
    val address: String? = null,
    val meetDay: LocalDate,
    val thumbnailLinks: ThumbnailLinks? = null,
    val password: Short,
    val voteDeadline: LocalDateTime,
    val roomId: UUID? = null,
)
