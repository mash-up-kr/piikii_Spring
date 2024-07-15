package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.ScheduleType
import java.util.UUID

data class PlaceResponse(
    val id: Long,
    val roomUid: UUID,
    val scheduleId: Long,
    val url: String? = null,
    val placeImageUrls: ThumbnailLinks,
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
        roomUid = place.roomUid,
        scheduleId = place.id,
        url = place.url,
        placeImageUrls = place.thumbnailLinks,
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
    val scheduleType: ScheduleType,
    val places: List<PlaceResponse>,
)
