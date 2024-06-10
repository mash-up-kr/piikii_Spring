package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.roomvote.RoomVote
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.UUID
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "room_vote", schema = "piikii")
@SQLRestriction("is_deleted = false")
@DynamicUpdate
class RoomVoteEntity(
    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(name = "room_place_id", nullable = false)
    val roomPlaceId: Long,

    @Column(name = "content", nullable = false, length = 10)
    val content: String
) : BaseEntity()

fun RoomVoteEntity.toDomain(): RoomVote {
    return RoomVote(
        userId = this.userId,
        roomPlaceId = this.roomPlaceId,
        content = this.content
    )
}

fun RoomVote.toEntity(): RoomVoteEntity {
    return RoomVoteEntity(
        userId = this.userId,
        roomPlaceId = this.roomPlaceId,
        content = this.content
    )
}
