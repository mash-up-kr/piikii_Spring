package com.piikii.application.domain.fixture

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.Place
import java.util.UUID

class PlaceFixture(
    private var id: LongTypeId = LongTypeId(0L),
    private var url: String? = null,
    private var name: String = "",
    private var thumbnailLinks: ThumbnailLinks = ThumbnailLinks(null),
    private var address: String? = null,
    private var phoneNumber: String? = null,
    private var starGrade: Float? = null,
    private var origin: Origin = Origin.MANUAL,
    private var roomUid: UuidTypeId = UuidTypeId(UUID.randomUUID()),
    private var memo: String? = null,
    private var reviewCount: Int = 0,
    private var longitude: Double? = null,
    private var latitude: Double? = null,
    private var openingHours: String? = null,
) {
    fun id(id: Long): PlaceFixture {
        this.id = LongTypeId(id)
        return this
    }

    fun origin(origin: Origin): PlaceFixture {
        this.origin = origin
        return this
    }

    fun roomUid(roomUid: UuidTypeId): PlaceFixture {
        this.roomUid = roomUid
        return this
    }

    fun longitude(longitude: Double): PlaceFixture {
        this.longitude = longitude
        return this
    }

    fun latitude(latitude: Double): PlaceFixture {
        this.latitude = latitude
        return this
    }

    fun build(): Place {
        return Place(
            id = this.id,
            url = this.url,
            name = this.name,
            thumbnailLinks = this.thumbnailLinks,
            address = this.address,
            phoneNumber = this.phoneNumber,
            starGrade = this.starGrade,
            origin = this.origin,
            roomUid = this.roomUid,
            memo = this.memo,
            reviewCount = this.reviewCount,
            longitude = this.longitude,
            latitude = this.latitude,
            openingHours = this.openingHours,
        )
    }

    companion object {
        fun create() = PlaceFixture()
    }
}
