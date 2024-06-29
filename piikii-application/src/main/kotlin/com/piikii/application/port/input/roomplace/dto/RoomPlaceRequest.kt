package com.piikii.application.port.input.roomplace.dto

import com.piikii.application.domain.generic.PlaceCategory
import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.roomplace.RoomPlace

data class AddRoomPlaceRequest(
    val url: String? = null,
    val thumbnailLinks: ThumbnailLinks,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val source: Source,
    val placeCategory: PlaceCategory,
) {
    fun toDomain(): RoomPlace {
        return RoomPlace(
            id = null,
            url = url,
            thumbnailLinks = thumbnailLinks,
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            source = source,
            placeCategory = placeCategory,
        )
    }
}

data class ModifyRoomPlaceRequest(
    val url: String,
    val thumbnailLinks: ThumbnailLinks,
    val address: String,
    val phoneNumber: String,
    val starGrade: Float,
    val source: Source,
    val placeCategory: PlaceCategory,
) {
    fun toDomain(targetRoomPlaceId: Long): RoomPlace {
        return RoomPlace(
            id = targetRoomPlaceId,
            url = url,
            thumbnailLinks = thumbnailLinks,
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            source = source,
            placeCategory = placeCategory,
        )
    }
}

data class DeleteRoomPlaceRequest(
    val targetRoomPlaceId: Long,
)
