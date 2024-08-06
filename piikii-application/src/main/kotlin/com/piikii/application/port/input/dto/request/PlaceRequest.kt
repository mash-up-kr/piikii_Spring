package com.piikii.application.port.input.dto.request

import com.piikii.application.domain.generic.Origin
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.ScheduleType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import java.util.UUID

data class AddPlaceRequest(
    @field:NotNull(message = "일정 ID는 필수입니다.")
    @field:Schema(description = "일정 ID", example = "1")
    val scheduleId: Long,
    @field:NotNull(message = "일정 타입은 필수입니다.")
    @field:Schema(
        description = "스케줄 타입",
        allowableValues = [
            "ARCADE",
            "DISH",
            "DESSERT",
            "ALCOHOL",
        ],
        example = "DISH",
    )
    val type: ScheduleType,
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
    @field:NotBlank(message = "메모는 필수이며 빈 문자열이 허용되지 않습니다.")
    @field:Size(max = 50, message = "메모는 50자를 초과할 수 없습니다.")
    @field:Schema(description = "메모", example = "맛있는 레스토랑")
    val memo: String,
    @field:PositiveOrZero(message = "좋아요 수는 0 이상이어야 합니다.")
    @field:Schema(description = "좋아요 수", example = "10")
    val voteLikeCount: Short?,
    @field:PositiveOrZero(message = "싫어요 수는 0 이상이어야 합니다.")
    @field:Schema(description = "싫어요 수", example = "2")
    val voteDislikeCount: Short?,
    @field:Schema(description = "장소 위치 경도", example = "126.9246033")
    val x: Double?,
    @field:Schema(description = "장소 위치 위도", example = "33.45241976")
    val y: Double?,
) {
    fun toDomain(
        roomUid: UUID,
        scheduleId: Long,
        imageUrls: List<String>,
    ): Place {
        return Place(
            id = 0L,
            roomUid = roomUid,
            scheduleId = scheduleId,
            name = name,
            url = url,
            thumbnailLinks = ThumbnailLinks(imageUrls),
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            origin = Origin.MANUAL,
            memo = memo,
            confirmed = false,
            longitude = x,
            latitude = y,
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
    @field:Schema(description = "삭제할 이미지 URL 리스트", example = "[https://example.com, https://example.com]")
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
    @field:NotBlank(message = "메모는 필수이며 빈 문자열이 허용되지 않습니다.")
    @field:Size(max = 50, message = "메모는 50자를 초과할 수 없습니다.")
    @field:Schema(description = "메모", example = "맛있는 레스토랑")
    val memo: String,
    @field:PositiveOrZero(message = "좋아요 수는 0 이상이어야 합니다.")
    @field:Schema(description = "좋아요 수", example = "10")
    val voteLikeCount: Short?,
    @field:PositiveOrZero(message = "싫어요 수는 0 이상이어야 합니다.")
    @field:Schema(description = "싫어요 수", example = "2")
    val voteDislikeCount: Short?,
    @field:Schema(description = "장소 위치 경도", example = "126.9246033")
    val x: Double?,
    @field:Schema(description = "장소 위치 위도", example = "33.45241976")
    val y: Double?,
) {
    fun toDomain(
        targetPlaceId: Long,
        roomUid: UUID,
        scheduleId: Long,
        updatedUrls: List<String>,
    ): Place {
        return Place(
            id = targetPlaceId,
            roomUid = roomUid,
            scheduleId = scheduleId,
            name = name,
            url = url,
            thumbnailLinks = ThumbnailLinks(updatedUrls),
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            origin = Origin.MANUAL,
            memo = memo,
            confirmed = false,
            longitude = x,
            latitude = y,
        )
    }
}
