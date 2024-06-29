package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.roomcategory.PlaceCategory
import com.piikii.application.domain.roomplace.RoomPlace
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.util.UUID

@Entity
@Table(name = "room_place", schema = "piikii")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE piikii.room_place SET is_deleted = true WHERE id = ?")
@DynamicUpdate
class RoomPlaceEntity(
    @Column(name = "roomId")
    var roomId: UUID,
    @Column(name = "url", length = 255)
    var url: String?,
    @Column(name = "thumbnail_links", nullable = false, length = 255)
    var thumbnailLinks: String,
    @Column(name = "address", length = 255)
    var address: String? = null,
    @Column(name = "phone_number", length = 15)
    var phoneNumber: String? = null,
    @Column(name = "star_grade")
    var starGrade: Float? = null,
    @Enumerated(EnumType.STRING)
    var source: Source,
    @Enumerated(EnumType.STRING)
    var placeCategory: PlaceCategory,
) : BaseEntity() {
    fun toDomain(): RoomPlace {
        return RoomPlace(
            id = id,
            url = url,
            thumbnailLinks = ThumbnailLinks(thumbnailLinks),
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            source = source,
            placeCategory = placeCategory,
        )
    }

    fun update(roomPlace: RoomPlace) {
        url = roomPlace.url
        thumbnailLinks = roomPlace.thumbnailLinks.contents ?: ""
        address = roomPlace.address
        phoneNumber = roomPlace.phoneNumber
        starGrade = roomPlace.starGrade
        source = roomPlace.source
        placeCategory = roomPlace.placeCategory
    }

    companion object {
        fun toEntity(
            roomId: UUID,
            roomPlace: RoomPlace,
        ): RoomPlaceEntity {
            return RoomPlaceEntity(
                roomId = roomId,
                url = roomPlace.url,
                thumbnailLinks = roomPlace.thumbnailLinks.contents ?: "",
                address = roomPlace.address,
                phoneNumber = roomPlace.phoneNumber,
                starGrade = roomPlace.starGrade,
                source = roomPlace.source,
                placeCategory = roomPlace.placeCategory,
            )
        }
    }
}
