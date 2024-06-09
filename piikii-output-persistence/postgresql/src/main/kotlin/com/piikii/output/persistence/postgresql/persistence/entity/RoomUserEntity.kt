package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.roomuser.RoomUser
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "room_user", schema = "piikii")
@SQLRestriction("is_deleted = false")
@DynamicUpdate
class RoomUserEntity(
    @Column(name = "room_id", nullable = false)
    val roomId: Long,

    @Column(name = "voted")
    val voted: Boolean = false
) : BaseEntity()

fun RoomUserEntity.toDomain(): RoomUser {
    return RoomUser(
        roomId = this.roomId,
        voted = this.voted
    )
}

fun RoomUser.toEntity(): RoomUserEntity {
    return RoomUserEntity(
        roomId = this.roomId,
        voted = this.voted
    )
}
