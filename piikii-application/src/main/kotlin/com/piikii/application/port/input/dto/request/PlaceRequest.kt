package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.PlaceType
import java.util.UUID

data class AddPlaceRequest(
    val url: String? = null,
    val thumbnailLinks: ThumbnailLinks,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val source: Source,
    val placeType: PlaceType,
) {
    fun toDomain(roomId: UUID): Place {
        return Place(
            id = null,
            url = url,
            thumbnailLinks = thumbnailLinks,
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            source = source,
            placeType = placeType,
            roomId = roomId,
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
    fun toDomain(
        roomId: UUID,
        targetPlaceId: Long,
    ): Place {
        return Place(
            id = targetPlaceId,
            url = url,
            thumbnailLinks = thumbnailLinks,
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            source = source,
            placeType = placeType,
            roomId = roomId,
        )
    }
}

data class DeletePlaceRequest(
    val targetPlaceId: Long,
)
