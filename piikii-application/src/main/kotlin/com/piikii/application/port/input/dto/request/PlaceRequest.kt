package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import java.util.UUID

data class AddPlaceRequest(
    @field:NotNull
    val scheduleId: Long,
    @field:Size(max = 255)
    val url: String?,
    @field:NotNull
    val thumbnailLinks: ThumbnailLinks,
    @field:Size(max = 255)
    val address: String?,
    @field:Size(max = 255)
    val phoneNumber: String?,
    @field:Min(0) @field:Max(5)
    val starGrade: Float?,
    @field:NotNull
    val source: Source,
    @field:NotBlank @field:Size(max = 1000)
    val note: String,
    @field:PositiveOrZero
    val voteLikeCount: Short?,
    @field:PositiveOrZero
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
    @field:NotNull
    val scheduleId: Long,
    @field:Size(max = 255)
    val url: String?,
    @field:NotNull
    val thumbnailLinks: ThumbnailLinks,
    @field:Size(max = 255)
    val address: String?,
    @field:Size(max = 255)
    val phoneNumber: String?,
    @field:Min(0) @field:Max(5)
    val starGrade: Float?,
    @field:NotNull
    val source: Source,
    @field:NotBlank @field:Size(max = 1000)
    val note: String,
    @field:PositiveOrZero
    val voteLikeCount: Short?,
    @field:PositiveOrZero
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
    @field:NotNull
    val targetPlaceId: Long,
)
