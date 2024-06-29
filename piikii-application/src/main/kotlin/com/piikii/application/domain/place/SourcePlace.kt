package com.piikii.application.domain.place

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks

class SourcePlace(
    val originMapId: Long,
    val url: String,
    val thumbnailLinks: ThumbnailLinks,
    val address: String? = null,
    val name: String,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val source: Source,
)
