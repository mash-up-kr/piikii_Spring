package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.PlaceType

data class PlaceResponse(
    val placeId: Long,
    val url: String? = null,
    val thumbnailLinks: ThumbnailLinks,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val source: Source,
) {
    constructor(place: Place) : this(
        placeId = place.id!!,
        url = place.url,
        thumbnailLinks = place.thumbnailLinks,
        address = place.address,
        phoneNumber = place.phoneNumber,
        starGrade = place.starGrade,
        source = place.source,
    )
}

data class PlaceTypeGroupResponse(
    val placeType: PlaceType,
    val places: List<PlaceResponse>,
) {
    companion object {
        fun groupingByPlaceType(places: List<Place>): List<PlaceTypeGroupResponse> {
            return places
                .groupBy { it.placeType }
                .map { (placeType, places) ->
                    PlaceTypeGroupResponse(
                        placeType = placeType,
                        places = places.map { PlaceResponse(it) },
                    )
                }
        }
    }
}
