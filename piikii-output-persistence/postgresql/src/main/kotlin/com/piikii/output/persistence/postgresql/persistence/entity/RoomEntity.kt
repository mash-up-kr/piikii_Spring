package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "room", schema = "piikii")
class RoomEntity(
    @Column(nullable = false, length = 255)
    val address: String,

    @Column(name = "meet_day", nullable = false)
    val meetDay: LocalDate,

    @Column(name = "thumbnail_link", length = 255)
    val thumbnailLink: String? = null,

    @Column(nullable = false)
    val password: Short,

    @Column(name = "vote_deadline", nullable = false)
    val voteDeadline: Date
) : BaseEntity()
