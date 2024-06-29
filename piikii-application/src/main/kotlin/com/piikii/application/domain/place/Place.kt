package com.piikii.application.domain.place

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.schedule.PlaceType

data class Place(
    val id: Long? = 0L,
    val placeType: PlaceType,
    val url: String? = null,
    val thumbnailLinks: ThumbnailLinks,
    val name: String,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val source: Source,
)
