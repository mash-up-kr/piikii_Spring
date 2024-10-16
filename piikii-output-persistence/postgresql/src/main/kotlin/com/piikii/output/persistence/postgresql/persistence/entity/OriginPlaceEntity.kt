package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.OriginMapId
import com.piikii.application.domain.place.OriginPlace
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "origin_place", schema = "piikii")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE piikii.origin_place SET is_deleted = true WHERE id = ?")
@DynamicUpdate
class OriginPlaceEntity(
    @Column(name = "origin_map_id", nullable = false, unique = true)
    val originMapId: OriginMapId,
    @Column(name = "name", length = 255, nullable = false)
    var name: String,
    @Column(name = "url", nullable = false, length = 255)
    val url: String,
    @Column(name = "thumbnail_links", columnDefinition = "TEXT")
    val thumbnailLinks: String,
    @Column(name = "address", length = 255)
    val address: String? = null,
    @Column(name = "phone_number", length = 15)
    val phoneNumber: String? = null,
    @Column(name = "star_grade")
    val starGrade: Double? = null,
    @Column(name = "longitude")
    val longitude: Double?,
    @Column(name = "latitude")
    val latitude: Double?,
    @Column(name = "review_count", nullable = false)
    val reviewCount: Int,
    @Column(name = "category")
    val category: String?,
    @Column(name = "opening_hours")
    val openingHours: String?,
    @Enumerated(EnumType.STRING)
    @Column(name = "origin", nullable = false)
    val origin: Origin,
) : BaseEntity() {
    fun toDomain(): OriginPlace {
        return OriginPlace(
            id = LongTypeId(id),
            originMapId = originMapId,
            name = name,
            url = url,
            thumbnailLinks = ThumbnailLinks(thumbnailLinks),
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            longitude = longitude,
            latitude = latitude,
            reviewCount = reviewCount,
            category = category,
            openingHours = openingHours,
            origin = origin,
        )
    }

    companion object {
        fun from(originPlace: OriginPlace): OriginPlaceEntity {
            return OriginPlaceEntity(
                originMapId = originPlace.originMapId,
                name = originPlace.name,
                url = originPlace.url,
                thumbnailLinks = originPlace.thumbnailLinks.contents.toString(),
                address = originPlace.address,
                phoneNumber = originPlace.phoneNumber,
                starGrade = originPlace.starGrade,
                longitude = originPlace.longitude,
                latitude = originPlace.latitude,
                reviewCount = originPlace.reviewCount,
                category = originPlace.category,
                openingHours = originPlace.openingHours,
                origin = originPlace.origin,
            )
        }
    }
}
