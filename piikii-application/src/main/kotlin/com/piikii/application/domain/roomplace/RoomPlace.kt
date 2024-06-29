package com.piikii.application.domain.roomplace

import com.piikii.application.domain.generic.PlaceCategory
import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks

data class RoomPlace(
    val id: Long?,
    val placeCategory: PlaceCategory,
    val url: String? = null,
    val thumbnailLinks: ThumbnailLinks,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val source: Source,
)
