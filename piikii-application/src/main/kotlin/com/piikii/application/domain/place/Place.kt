package com.piikii.application.domain.place

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.schedule.PlaceType
import java.util.UUID

data class Place(
    val id: Long? = 0L,
    val placeType: PlaceType,
    val url: String? = null,
    val thumbnailLinks: ThumbnailLinks,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val source: Source,
    val roomId: UUID,
)
