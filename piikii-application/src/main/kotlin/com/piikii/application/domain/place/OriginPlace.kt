package com.piikii.application.domain.place

import com.piikii.application.domain.generic.Origin
import com.piikii.application.domain.generic.ThumbnailLinks

data class OriginPlace(
    val id: Long?,
    val name: String,
    val originMapId: Long,
    val url: String,
    val thumbnailLinks: ThumbnailLinks,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val origin: Origin,
)
