package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "room_vote", schema = "piikii")
class RoomVoteEntity(
    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(name = "room_place_id", nullable = false)
    val roomPlaceId: Long,

    @Column(nullable = false, length = 10)
    val content: String
) : BaseEntity()
