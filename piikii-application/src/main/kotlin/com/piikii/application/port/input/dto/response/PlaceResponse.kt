package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.PlaceType
import com.piikii.application.domain.schedule.Schedule
import java.util.UUID

data class PlaceResponse(
    val id: Long,
    val roomId: UUID,
    val scheduleId: Long,
    val url: String? = null,
    val thumbnailLinks: ThumbnailLinks,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float?,
    val source: Source,
    val note: String?,
    val voteLikeCount: Short?,
    val voteDislikeCount: Short?,
) {
    constructor(place: Place) : this(
        id = place.id,
        roomId = place.roomId,
        scheduleId = place.scheduleId,
        url = place.url,
        thumbnailLinks = place.thumbnailLinks,
        address = place.address,
        phoneNumber = place.phoneNumber,
        starGrade = place.starGrade,
        source = place.source,
        note = place.note,
        voteLikeCount = place.voteLikeCount,
        voteDislikeCount = place.voteDislikeCount,
    )
}

data class PlaceTypeGroupResponse(
    val placeType: PlaceType,
    val places: List<PlaceResponse>,
) {
    companion object {
        fun groupingByPlaceType(placesScheduleMap: Map<Place, Schedule>): List<PlaceTypeGroupResponse> {
            return placesScheduleMap.entries
                .groupBy { (place, schedule) -> schedule.placeType }
                .map { (placeType, entries) ->
                    PlaceTypeGroupResponse(
                        placeType = placeType,
                        places = entries.map { (place, _) -> PlaceResponse(place) },
                    )
                }
        }
    }
}
