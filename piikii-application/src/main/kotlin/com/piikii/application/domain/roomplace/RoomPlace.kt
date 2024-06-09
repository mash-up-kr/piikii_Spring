package com.piikii.application.domain.roomplace

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks

class RoomPlace(
    val roomId: Long,
    val url: String,
    val thumbnailLinks: ThumbnailLinks,
    val address: String? = null,
    val phoneNumber: String? = null,
    val starGrade: Float? = null,
    val source: Source
) {
}
