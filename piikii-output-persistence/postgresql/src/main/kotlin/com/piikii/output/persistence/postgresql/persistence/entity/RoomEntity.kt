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
) : BaseEntity() {

    //TODO: 모임 이름, 하고 싶은 메시지 칼럼 추가

    fun update(room: Room) {
        this.address = room.address
        this.meetDay = room.meetDay
        this.password = room.password
        this.voteDeadline = room.voteDeadline
        //TODO: 이미지 storage 확정 후 thumbnailLinks 추가 처리
        //TODO: DTO 내 nullable 필드가 없으므로 우선 null 처리 제외
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
        address = this.address,
        meetDay = this.meetDay,
        thumbnailLinks = this.thumbnailLinks.contents.toString(),
        password = this.password,
        voteDeadline = this.voteDeadline,
    )
}
