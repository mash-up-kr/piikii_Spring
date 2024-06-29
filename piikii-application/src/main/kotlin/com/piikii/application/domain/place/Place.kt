package com.piikii.application.domain.place

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.schedule.Schedule

data class Place(
    val id: Long? = 0L,
    val url: String? = null,
    val thumbnailLinks: ThumbnailLinks,
    val name: String,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val source: Source,
    val voteLikeCount: Int? = null,
    val schedule: Schedule?,
)
