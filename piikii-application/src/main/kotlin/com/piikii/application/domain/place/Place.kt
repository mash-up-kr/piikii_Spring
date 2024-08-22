package com.piikii.application.domain.place

import com.piikii.application.domain.course.Coordinate
import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.generic.UuidTypeId

data class Place(
    val id: LongTypeId,
    val roomUid: UuidTypeId,
    val name: String,
    val url: String?,
    val thumbnailLinks: ThumbnailLinks,
    val address: String?,
    val phoneNumber: String?,
    val starGrade: Float?,
    val origin: Origin,
    val memo: String?,
    val reviewCount: Int?,
    val longitude: Double?,
    val latitude: Double?,
    val openingHours: String?,
) {
    fun getCoordinate(): Coordinate {
        return Coordinate(this.longitude, this.latitude)
    }
}
