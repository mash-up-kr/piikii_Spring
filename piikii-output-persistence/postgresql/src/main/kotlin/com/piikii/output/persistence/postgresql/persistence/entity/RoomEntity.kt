package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.room.Password
import com.piikii.application.domain.room.Room
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLRestriction
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "room", schema = "piikii")
@SQLRestriction("is_deleted = false")
@DynamicUpdate
class RoomEntity(
    @Column(name = "address", nullable = false, length = 255)
    var address: String,
    @Column(name = "meet_day", nullable = false)
    var meetDay: LocalDate,
    @Column(name = "thumbnail_link", length = 255)
    var thumbnailLink: String,
    @Column(name = "password", nullable = false, length = 4)
    var password: Password,
    @Column(name = "vote_deadline")
    var voteDeadline: LocalDateTime?,
    @Column(name = "room_id", nullable = false, unique = true)
    val roomId: UUID,
    @Column(name = "meeting_name", nullable = false)
    var meetingName: String,
    @Column(name = "message")
    var message: String?,
    @Column(name = "is_course_created", nullable = false)
    var isCourseCreated: Boolean = false,
) : BaseEntity() {
    fun toDomain(): Room {
        return Room(
            meetingName = this.meetingName,
            message = this.message,
            address = this.address,
            meetDay = this.meetDay,
            thumbnailLink = this.thumbnailLink,
            password = this.password,
            voteDeadline = voteDeadline,
            roomId = this.roomId,
            isCourseCreated = this.isCourseCreated,
        )
    }

    fun update(room: Room) {
        this.thumbnailLink = room.thumbnailLink
        this.meetingName = room.meetingName
        this.message = room.message ?: this.message
        this.password = room.password
        this.address = room.address
        this.meetDay = room.meetDay
        this.voteDeadline = room.voteDeadline
    }

    companion object {
        fun from(room: Room): RoomEntity {
            return RoomEntity(
                thumbnailLink = room.thumbnailLink,
                meetingName = room.meetingName,
                message = room.message,
                password = room.password,
                address = room.address,
                meetDay = room.meetDay,
                voteDeadline = room.voteDeadline,
                roomId = room.roomId,
                isCourseCreated = room.isCourseCreated,
            )
        }
    }
}
