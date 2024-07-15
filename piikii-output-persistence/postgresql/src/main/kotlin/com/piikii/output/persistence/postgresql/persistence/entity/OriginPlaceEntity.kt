package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.generic.Origin
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.OriginPlace
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "origin_place", schema = "piikii")
@SQLRestriction("is_deleted = false")
class OriginPlaceEntity(
    @Column(name = "origin_map_id", nullable = false)
    val originMapId: Long,
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
    val origin: Origin,
) : BaseEntity() {
    fun toDomain(): OriginPlace {
        return OriginPlace(
            originMapId = this.originMapId,
            url = this.url,
            thumbnailLinks = ThumbnailLinks(this.thumbnailLinks),
            address = this.address,
            phoneNumber = this.phoneNumber,
            starGrade = this.starGrade,
            origin = this.origin,
        )
    }

    companion object {
        fun from(originPlace: OriginPlace): OriginPlaceEntity {
            return OriginPlaceEntity(
                originMapId = originPlace.originMapId,
                url = originPlace.url,
                thumbnailLinks = originPlace.thumbnailLinks.contents.toString(),
                address = originPlace.address,
                phoneNumber = originPlace.phoneNumber,
                starGrade = originPlace.starGrade,
                origin = originPlace.origin,
            )
        }
    }
}
