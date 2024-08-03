package com.piikii.application.domain.place

import com.piikii.application.domain.generic.Origin
import com.piikii.application.domain.generic.ThumbnailLinks
import java.util.UUID

data class Place(
    val id: Long,
    val roomUid: UUID,
    val scheduleId: Long,
    val name: String,
    val url: String?,
    val thumbnailLinks: ThumbnailLinks,
    val address: String?,
    val phoneNumber: String?,
    val starGrade: Float?,
    val origin: Origin,
    val memo: String?,
    val confirmed: Boolean,
)
