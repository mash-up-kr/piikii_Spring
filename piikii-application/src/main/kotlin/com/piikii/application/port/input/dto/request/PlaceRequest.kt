package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import java.util.UUID

data class AddPlaceRequest(
    @field:NotNull
    @Schema(description = "일정 ID", example = "1")
    val scheduleId: Long,
    @field:Size(max = 255)
    @Schema(description = "장소 URL", example = "https://example.com")
    val url: String?,
    @field:Size(max = 255)
    @Schema(description = "주소", example = "서울시 강남구")
    val address: String?,
    @field:Size(max = 255)
    @Schema(description = "전화번호", example = "02-1234-5678")
    val phoneNumber: String?,
    @field:Min(0) @field:Max(5)
    @Schema(description = "별점 (0-5)", example = "4.5")
    val starGrade: Float?,
    @field:NotNull
    @Schema(description = "출처")
    val source: Source,
    @field:NotBlank @field:Size(max = 50)
    @Schema(description = "메모", example = "맛있는 레스토랑")
    val note: String,
    @field:PositiveOrZero
    @Schema(description = "좋아요 수", example = "10")
    val voteLikeCount: Short?,
    @field:PositiveOrZero
    @Schema(description = "싫어요 수", example = "2")
    val voteDislikeCount: Short?,
) {
    fun toDomain(
        roomId: UUID,
        imageUrls: List<String>,
    ): Place {
        return Place(
            id = 0L,
            roomId = roomId,
            scheduleId = scheduleId,
            url = url,
            thumbnailLinks = ThumbnailLinks(imageUrls),
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
    @Schema(description = "일정 ID", example = "1")
    val scheduleId: Long,
    @field:Size(max = 255)
    @Schema(description = "장소 URL", example = "https://example.com")
    val url: String?,
    @Schema(description = "삭제할 이미지 URL 리스트", example = "[https://example.com, https://example.com]")
    val deleteTargetUrls: List<String>,
    @field:NotNull
    @field:Size(max = 255)
    @Schema(description = "주소", example = "서울시 강남구")
    val address: String?,
    @field:Size(max = 255)
    @Schema(description = "전화번호", example = "02-1234-5678")
    val phoneNumber: String?,
    @field:Min(0) @field:Max(5)
    @Schema(description = "별점 (0-5)", example = "4.5")
    val starGrade: Float?,
    @field:NotNull
    @Schema(description = "출처")
    val source: Source,
    @field:NotBlank @field:Size(max = 50)
    @Schema(description = "메모", example = "맛있는 레스토랑")
    val note: String,
    @field:PositiveOrZero
    @Schema(description = "좋아요 수", example = "10")
    val voteLikeCount: Short?,
    @field:PositiveOrZero
    @Schema(description = "싫어요 수", example = "2")
    val voteDislikeCount: Short?,
) {
    fun toDomain(
        targetPlaceId: Long,
        roomId: UUID,
        updatedUrls: List<String>,
    ): Place {
        return Place(
            id = targetPlaceId,
            roomId = roomId,
            scheduleId = scheduleId,
            url = url,
            thumbnailLinks = ThumbnailLinks(updatedUrls),
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
    @Schema(description = "삭제할 장소 ID", example = "1")
    val targetPlaceId: Long,
)
