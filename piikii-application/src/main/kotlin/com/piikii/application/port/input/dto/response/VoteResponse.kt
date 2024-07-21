package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.generic.Origin
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place

data class VoteStatusResponse(val voteFinished: Boolean)

data class VoteResultResponse(
    val result: List<VoteResultByScheduleResponse>,
)

data class VoteResultByScheduleResponse(
    val scheduleId: Long,
    val scheduleName: String,
    val places: List<VotePlaceResponse>,
)

data class VotePlaceResponse(
    val placeId: Long,
    val name: String,
    val url: String?,
    val thumbnailLinks: ThumbnailLinks,
    val address: String?,
    val phoneNumber: String?,
    val starGrade: Float?,
    val origin: Origin,
    val memo: String?,
    val countOfAgree: Int,
) {
    constructor(place: Place, countOfAgree: Int) : this(
        placeId = place.id,
        name = place.name,
        url = place.url,
        thumbnailLinks = place.thumbnailLinks,
        address = place.address,
        phoneNumber = place.phoneNumber,
        starGrade = place.starGrade,
        origin = place.origin,
        memo = place.memo,
        countOfAgree = countOfAgree,
    )
}
