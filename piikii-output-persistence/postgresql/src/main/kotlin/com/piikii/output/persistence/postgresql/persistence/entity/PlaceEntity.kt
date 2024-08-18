package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.Place
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
@Table(name = "place", schema = "piikii")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE piikii.place SET is_deleted = true WHERE id = ?")
@DynamicUpdate
class PlaceEntity(
    @Column(name = "room_uid", nullable = false, updatable = false)
    val roomUid: UuidTypeId,
    @Column(name = "schedule_id", nullable = false, updatable = false)
    var scheduleId: LongTypeId,
    @Column(name = "name", length = 255, nullable = false)
    var name: String,
    @Column(name = "url", length = 255)
    var url: String?,
    @Column(name = "thumbnail_links", columnDefinition = "TEXT")
    var thumbnailLinks: String?,
    @Column(name = "address", length = 255)
    var address: String?,
    @Column(name = "phone_number", length = 15)
    var phoneNumber: String?,
    @Column(name = "star_grade", nullable = false)
    var starGrade: Float?,
    @Enumerated(EnumType.STRING)
    @Column(name = "origin", nullable = false)
    var origin: Origin,
    @Column(name = "memo", length = 150)
    var memo: String?,
    @Column(name = "confirmed", nullable = false)
    var confirmed: Boolean = false,
    @Column(name = "review_count", nullable = false)
    val reviewCount: Int?,
    @Column(name = "longitude")
    val longitude: Double?,
    @Column(name = "latitude")
    val latitude: Double?,
    @Column(name = "opening_hours")
    val openingHours: String?,
) : BaseEntity() {
    constructor(roomUid: UuidTypeId, scheduleId: LongTypeId, place: Place) : this(
        roomUid = roomUid,
        scheduleId = scheduleId,
        name = place.name,
        url = place.url,
        thumbnailLinks = place.thumbnailLinks.contents ?: "",
        address = place.address,
        phoneNumber = place.phoneNumber,
        starGrade = place.starGrade,
        origin = place.origin,
        memo = place.memo,
        confirmed = place.confirmed,
        reviewCount = place.reviewCount,
        longitude = place.longitude,
        latitude = place.latitude,
        openingHours = place.openingHours,
    )

    fun toDomain(): Place {
        return Place(
            id = LongTypeId(id),
            roomUid = roomUid,
            scheduleId = scheduleId,
            name = name,
            url = url,
            thumbnailLinks = ThumbnailLinks(thumbnailLinks),
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            origin = origin,
            memo = memo,
            confirmed = confirmed,
            reviewCount = reviewCount,
            longitude = longitude,
            latitude = latitude,
            openingHours = openingHours,
        )
    }

    fun update(place: Place) {
        url = place.url
        thumbnailLinks = place.thumbnailLinks.contents ?: ""
        address = place.address
        phoneNumber = place.phoneNumber
        starGrade = place.starGrade
        origin = place.origin
        memo = place.memo
        confirmed = place.confirmed
    }
}
