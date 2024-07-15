package com.piikii.application.domain.place

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.schedule.PlaceType
import java.util.UUID

data class Place(
    val id: Long,
    val roomUid: UUID,
    val scheduleId: Long,
    val placeType: PlaceType,
    val url: String? = null,
    val thumbnailLinks: ThumbnailLinks,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = 0.0F,
    val source: Source,
    val note: String?,
    val voteLikeCount: Short? = 0,
    val voteDislikeCount: Short? = 0,
)
