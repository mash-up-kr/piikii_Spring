package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place
import java.util.UUID

data class AddPlaceRequest(
    val scheduleId: Long,
    val url: String?,
    val thumbnailLinks: ThumbnailLinks,
    val address: String?,
    val phoneNumber: String?,
    val starGrade: Float?,
    val source: Source,
    val note: String,
    val voteLikeCount: Short?,
    val voteDislikeCount: Short?,
) {
    fun toDomain(roomId: UUID): Place {
        return Place(
            id = 0L,
            roomId = roomId,
            scheduleId = scheduleId,
            url = url,
            thumbnailLinks = thumbnailLinks,
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            source = source,
            note = note,
            voteLikeCount = voteLikeCount,
            voteDislikeCount = voteDislikeCount,
        )
    }
}

data class ModifyPlaceRequest(
    val scheduleId: Long,
    val url: String?,
    val thumbnailLinks: ThumbnailLinks,
    val address: String?,
    val phoneNumber: String?,
    val starGrade: Float?,
    val source: Source,
    val note: String,
    val voteLikeCount: Short?,
    val voteDislikeCount: Short?,
) {
    fun toDomain(
        targetPlaceId: Long,
        roomId: UUID,
    ): Place {
        return Place(
            id = targetPlaceId,
            roomId = roomId,
            scheduleId = scheduleId,
            url = url,
            thumbnailLinks = thumbnailLinks,
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            source = source,
            note = note,
            voteLikeCount = voteLikeCount,
            voteDislikeCount = voteDislikeCount,
        )
    }
}

data class DeletePlaceRequest(
    val targetPlaceId: Long,
)
