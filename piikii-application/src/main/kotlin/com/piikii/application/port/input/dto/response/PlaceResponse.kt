package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.generic.Origin
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.ScheduleType
import java.util.UUID

data class PlaceResponse(
    val id: Long,
    val roomUid: UUID,
    val scheduleId: Long,
    val url: String?,
    val placeImageUrls: ThumbnailLinks,
    val address: String?,
    val phoneNumber: String?,
    val starGrade: Float?,
    val origin: Origin,
    val memo: String?,
) {
    constructor(place: Place) : this(
        id = place.id,
        roomUid = place.roomUid,
        scheduleId = place.id,
        url = place.url,
        placeImageUrls = place.thumbnailLinks,
        address = place.address,
        phoneNumber = place.phoneNumber,
        starGrade = place.starGrade,
        origin = place.origin,
        memo = place.memo,
    )
}

data class ScheduleTypeGroupResponse(
    val scheduleType: ScheduleType,
    val places: List<PlaceResponse>,
)
