package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.PlaceType
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
@Table(name = "place", schema = "piikii")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE piikii.place SET is_deleted = true WHERE id = ?")
@DynamicUpdate
class PlaceEntity(
    @Column(name = "roomId")
    val roomId: UUID,
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
    var placeType: PlaceType,
) : BaseEntity() {
    fun toDomain(): Place {
        return Place(
            id = id,
            url = url,
            thumbnailLinks = ThumbnailLinks(thumbnailLinks),
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            source = source,
            placeType = placeType,
        )
    }

    fun update(place: Place) {
        url = place.url
        thumbnailLinks = place.thumbnailLinks.contents ?: ""
        address = place.address
        phoneNumber = place.phoneNumber
        starGrade = place.starGrade
        source = place.source
        placeType = place.placeType
    }

    companion object {
        fun of(
            roomId: UUID,
            place: Place,
        ): PlaceEntity {
            return PlaceEntity(
                roomId = roomId,
                url = place.url,
                thumbnailLinks = place.thumbnailLinks.contents ?: "",
                address = place.address,
                phoneNumber = place.phoneNumber,
                starGrade = place.starGrade,
                source = place.source,
                placeType = place.placeType,
            )
        }
    }
}
