package com.piikii.application.domain.room

import com.piikii.application.domain.generic.ThumbnailLinks
import java.time.LocalDate
import java.time.LocalDateTime

class Room(
    val address: String,
    val meetDay: LocalDate,
    val thumbnailLinks: ThumbnailLinks,
    val password: Short,
    val voteDeadline: LocalDateTime,
)
