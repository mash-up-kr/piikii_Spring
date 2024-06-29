package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.PlaceType

data class AddPlaceRequest(
    val url: String? = null,
    val thumbnailLinks: ThumbnailLinks,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val source: Source,
    val placeType: PlaceType,
) {
    fun toDomain(): Place {
        return Place(
            id = null,
            url = url,
            thumbnailLinks = thumbnailLinks,
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            source = source,
            placeType = placeType,
        )
    }
}

data class ModifyPlaceRequest(
    val url: String,
    val thumbnailLinks: ThumbnailLinks,
    val address: String,
    val phoneNumber: String,
    val starGrade: Float,
    val source: Source,
    val placeType: PlaceType,
) {
    fun toDomain(targetPlaceId: Long): Place {
        return Place(
            id = targetPlaceId,
            url = url,
            thumbnailLinks = thumbnailLinks,
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            source = source,
            placeType = placeType,
        )
    }
}

data class DeletePlaceRequest(
    val targetPlaceId: Long,
)
