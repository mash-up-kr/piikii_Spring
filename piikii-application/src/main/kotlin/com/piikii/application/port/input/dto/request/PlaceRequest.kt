package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.ScheduleType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

data class AddPlaceRequest(
    @field:NotNull(message = "일정 ID는 필수입니다.")
    @field:Schema(description = "일정 ID", example = "[1, 2, 3]")
    val scheduleIds: List<Long>,
    @field:NotBlank(message = "장소 이름은 필수이며 빈 문자열이 허용되지 않습니다.")
    @field:Size(max = 255, message = "장소 이름은 255자를 초과할 수 없습니다.")
    @field:Schema(description = "장소 이름", example = "보배네 맛집")
    val name: String,
    @field:Size(max = 255, message = "URL은 255자를 초과할 수 없습니다.")
    @field:Schema(description = "장소 URL", example = "https://example.com")
    val url: String?,
    @field:Size(max = 255, message = "주소는 255자를 초과할 수 없습니다.")
    @field:Schema(description = "주소", example = "서울시 강남구")
    val address: String?,
    @field:Size(max = 255, message = "전화번호는 255자를 초과할 수 없습니다.")
    @field:Schema(description = "전화번호", example = "02-1234-5678")
    val phoneNumber: String?,
    @field:Min(value = 0, message = "별점은 0 이상이어야 합니다.")
    @field:Max(value = 5, message = "별점은 5 이하여야 합니다.")
    @field:Schema(description = "별점 (0-5)", example = "4.5")
    val starGrade: Float?,
    @field:Size(max = 50, message = "메모는 50자를 초과할 수 없습니다.")
    @field:Schema(description = "메모", example = "맛있는 레스토랑")
    val memo: String?,
    @field:PositiveOrZero(message = "좋아요 수는 0 이상이어야 합니다.")
    @field:Schema(description = "좋아요 수", example = "10")
    val voteLikeCount: Short?,
    @field:PositiveOrZero(message = "싫어요 수는 0 이상이어야 합니다.")
    @field:Schema(description = "싫어요 수", example = "2")
    val voteDislikeCount: Short?,
    @field:PositiveOrZero(message = "리뷰 수는 0 이상이어야 합니다.")
    @field:Schema(description = "리뷰 수", example = "2")
    val reviewCount: Int? = 0,
    @field:Schema(description = "장소 위치 경도", example = "126.9246033")
    val longitude: Double?,
    @field:Schema(description = "장소 위치 위도", example = "33.45241976")
    val latitude: Double?,
    @field:Schema(description = "영업시간", example = "10:00 ~ 17:00")
    val openingHours: String?,
) {
    fun toDomain(
        roomUid: UuidTypeId,
        imageUrls: List<String>,
    ): Place {
        return Place(
            id = LongTypeId(0L),
            roomUid = roomUid,
            name = name,
            url = url,
            thumbnailLinks = ThumbnailLinks(imageUrls),
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            origin = Origin.MANUAL,
            memo = memo,
            reviewCount = reviewCount,
            longitude = longitude,
            latitude = latitude,
            openingHours = openingHours,
        )
    }
}

data class ModifyPlaceRequest(
    @field:NotNull(message = "일정 ID는 필수입니다.")
    @field:Schema(description = "일정 ID", example = "1")
    val scheduleId: Long,
    @field:NotNull(message = "일정 타입은 필수입니다.")
    @field:Schema(description = "일정 타입", example = "MEAL")
    val scheduleType: ScheduleType,
    @field:NotBlank(message = "장소 이름은 필수이며 빈 문자열이 허용되지 않습니다.")
    @field:Size(max = 255, message = "장소 이름은 255자를 초과할 수 없습니다.")
    @field:Schema(description = "장소 이름", example = "보배네 맛집")
    val name: String,
    @field:Size(max = 255, message = "URL은 255자를 초과할 수 없습니다.")
    @field:Schema(description = "장소 URL", example = "https://example.com")
    val url: String?,
    @field:Schema(description = "삭제할 이미지 URL 리스트", example = "https://example.com, https://example.com")
    val deleteTargetUrls: List<String>,
    @field:NotNull(message = "주소는 필수입니다.")
    @field:Size(max = 255, message = "주소는 255자를 초과할 수 없습니다.")
    @field:Schema(description = "주소", example = "서울시 강남구")
    val address: String?,
    @field:Size(max = 255, message = "전화번호는 255자를 초과할 수 없습니다.")
    @field:Schema(description = "전화번호", example = "02-1234-5678")
    val phoneNumber: String?,
    @field:Min(value = 0, message = "별점은 0 이상이어야 합니다.")
    @field:Max(value = 5, message = "별점은 5 이하여야 합니다.")
    @field:Schema(description = "별점 (0-5)", example = "4.5")
    val starGrade: Float?,
    @field:Size(max = 50, message = "메모는 50자를 초과할 수 없습니다.")
    @field:Schema(description = "메모", example = "맛있는 레스토랑")
    val memo: String?,
    @field:PositiveOrZero(message = "좋아요 수는 0 이상이어야 합니다.")
    @field:Schema(description = "좋아요 수", example = "10")
    val voteLikeCount: Short?,
    @field:PositiveOrZero(message = "싫어요 수는 0 이상이어야 합니다.")
    @field:Schema(description = "싫어요 수", example = "2")
    val voteDislikeCount: Short?,
    @field:Schema(description = "리뷰 개수", example = "100")
    val reviewCount: Int? = 0,
    @field:Schema(description = "장소 위치 경도", example = "126.9246033")
    val longitude: Double?,
    @field:Schema(description = "장소 위치 위도", example = "33.45241976")
    val latitude: Double?,
    @field:Schema(description = "영업시간", example = "10:00 ~ 17:00")
    val openingHours: String?,
) {
    fun toDomain(
        targetPlaceId: LongTypeId,
        roomUid: UuidTypeId,
        updatedUrls: List<String>,
    ): Place {
        return Place(
            id = targetPlaceId,
            roomUid = roomUid,
            name = name,
            url = url,
            thumbnailLinks = ThumbnailLinks(updatedUrls),
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            origin = Origin.MANUAL,
            memo = memo,
            reviewCount = reviewCount,
            longitude = longitude,
            latitude = latitude,
            openingHours = openingHours,
        )
    }
}
