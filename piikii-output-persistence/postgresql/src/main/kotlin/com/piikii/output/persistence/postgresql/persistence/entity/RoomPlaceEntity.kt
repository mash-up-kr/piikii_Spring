package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.roomplace.RoomPlace
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "room_place", schema = "piikii")
@SQLRestriction("is_deleted = false")
@DynamicUpdate
class RoomPlaceEntity(
    @Column(name = "room_id", nullable = false)
    val roomId: Long,
    @Column(name = "url", nullable = false, length = 255)
    val url: String,
    @Column(name = "thumbnail_links", nullable = false, length = 255)
    val thumbnailLinks: String,
    @Column(name = "address", length = 255)
    val address: String? = null,
    @Column(name = "phone_number", length = 15)
    val phoneNumber: String? = null,
    @Column(name = "star_grade")
    val starGrade: Float? = null,
    @Enumerated(EnumType.STRING)
    val source: Source,
) : BaseEntity() {
    fun toDomain(): RoomPlace {
        return RoomPlace(
            roomId = this.roomId,
            url = this.url,
            thumbnailLinks = ThumbnailLinks(this.thumbnailLinks),
            address = this.address,
            phoneNumber = this.phoneNumber,
            starGrade = this.starGrade,
            source = this.source,
        )
    }

    companion object {
        fun toEntity(roomPlace: RoomPlace): RoomPlaceEntity {
            return RoomPlaceEntity(
                roomId = roomPlace.roomId,
                url = roomPlace.url,
                thumbnailLinks = roomPlace.thumbnailLinks.contents.toString(),
                address = roomPlace.address,
                phoneNumber = roomPlace.phoneNumber,
                starGrade = roomPlace.starGrade,
                source = roomPlace.source,
            )
        }
    }
}
