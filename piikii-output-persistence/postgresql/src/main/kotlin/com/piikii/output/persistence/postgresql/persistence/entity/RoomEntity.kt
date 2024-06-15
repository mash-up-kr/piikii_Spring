package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.generic.ThumbnailLinks
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
    @Column(name = "thumbnail_links", nullable = false, length = 255)
    val thumbnailLinks: String,
    @Column(name = "password", nullable = false)
    var password: Short,
    @Column(name = "vote_deadline", nullable = false)
    var voteDeadline: LocalDateTime,
    @Column(name = "room_id", nullable = false)
    val roomId: UUID,
    @Column(name = "meeting_name", nullable = false)
    var meetingName: String,
    @Column(name = "message")
    var message: String?,
) : BaseEntity() {
    fun update(room: Room) {
        this.meetingName = room.meetingName ?: this.meetingName
        this.message = room.message ?: this.message
    }
}

fun RoomEntity.toDomain(): Room {
    return Room(
        address = this.address,
        meetDay = this.meetDay,
        thumbnailLinks = ThumbnailLinks(this.thumbnailLinks),
        password = this.password,
        voteDeadline = this.voteDeadline,
    )
}

fun Room.toEntity(): RoomEntity {
    return RoomEntity(
        meetingName = this.meetingName!!,
        message = this.message,
        password = this.password!!,
        roomId = UUID.randomUUID(),
        // TODO: 기능&ERD 확정 후 정리
        address = this.address ?: "",
        meetDay = this.meetDay,
        thumbnailLinks = this.thumbnailLinks?.contents ?: "",
        voteDeadline = this.voteDeadline ?: LocalDateTime.now(),
    )
}
