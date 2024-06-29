package com.piikii.application.port.input.roomplace.dto

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.roomcategory.PlaceCategory
import com.piikii.application.domain.roomplace.RoomPlace

data class RoomPlaceResponse(
    val roomPlaceId: Long,
    val url: String? = null,
    val thumbnailLinks: ThumbnailLinks,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val source: Source,
) {
    constructor(roomPlace: RoomPlace) : this(
        roomPlaceId = roomPlace.id!!,
        url = roomPlace.url,
        thumbnailLinks = roomPlace.thumbnailLinks,
        address = roomPlace.address,
        phoneNumber = roomPlace.phoneNumber,
        starGrade = roomPlace.starGrade,
        source = roomPlace.source,
    )
}

data class RoomPlaceCategoryGroupResponse(
    val placeCategory: PlaceCategory,
    val roomPlaces: List<RoomPlaceResponse>,
) {
    companion object {
        fun groupingByCategoryName(roomPlaces: List<RoomPlace>): List<RoomPlaceCategoryGroupResponse> {
            return roomPlaces
                .groupBy { it.placeCategory }
                .map { (category, places) ->
                    RoomPlaceCategoryGroupResponse(
                        placeCategory = category,
                        roomPlaces = places.map { RoomPlaceResponse(it) },
                    )
                }
        }
    }
}
