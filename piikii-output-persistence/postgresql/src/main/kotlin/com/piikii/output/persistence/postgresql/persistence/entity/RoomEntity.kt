package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.room.Password
import com.piikii.application.domain.room.Room
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDateTime

@Entity
@Table(name = "room", schema = "piikii")
@SQLRestriction("is_deleted = false")
@DynamicUpdate
class RoomEntity(
    @Column(name = "room_uid", nullable = false, unique = true)
    val roomUid: UuidTypeId,
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "thumbnail_link", nullable = false, length = 255)
    var thumbnailLink: String,
    @Column(name = "password", nullable = false, length = 4)
    var password: Password,
    @Column(name = "vote_deadline")
    var voteDeadline: LocalDateTime?,
    @Column(name = "message")
    var message: String?,
) : BaseEntity() {
    fun toDomain(): Room {
        return Room(
            name = this.name,
            message = this.message,
            thumbnailLink = this.thumbnailLink,
            password = this.password,
            voteDeadline = voteDeadline,
            roomUid = this.roomUid,
        )
    }

    fun update(room: Room) {
        this.thumbnailLink = room.thumbnailLink
        this.name = room.name
        this.message = room.message ?: this.message
        this.password = room.password
        this.voteDeadline = room.voteDeadline
    }

    companion object {
        fun from(room: Room): RoomEntity {
            return RoomEntity(
                thumbnailLink = room.thumbnailLink,
                name = room.name,
                message = room.message,
                password = room.password,
                voteDeadline = room.voteDeadline,
                roomUid = room.roomUid,
            )
        }
    }
}
