package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.place.SchedulePlace
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "장소 정보 응답")
data class PlaceResponse(
    @field:Schema(description = "장소 ID", example = "1")
    val id: Long,
    @field:Schema(description = "방 고유 식별자", example = "123e4567-e89b-12d3-a456-426614174000")
    val roomUid: UUID,
    @field:Schema(description = "일정 ID", example = "1")
    val scheduleId: Long,
    @field:Schema(description = "장소 이름", example = "소현이네 주막")
    val name: String,
    @field:Schema(description = "장소 URL", example = "https://example.com/place")
    val url: String?,
    @field:Schema(description = "장소 이미지 URL 목록")
    val placeImageUrls: ThumbnailLinks,
    @field:Schema(description = "장소 주소", example = "서울시 강남구 테헤란로 123")
    val address: String?,
    @field:Schema(description = "전화번호", example = "02-1234-5678")
    val phoneNumber: String?,
    @field:Schema(description = "별점 (0-5)", example = "4.5")
    val starGrade: Float?,
    @field:Schema(
        description = "장소 정보 제공처",
        example = "MANUAL",
    )
    val origin: Origin,
    @field:Schema(description = "메모", example = "여기 가보자잇")
    val memo: String?,
    @field:Schema(description = "확정된 장소인지?", example = "false")
    var confirmed: Boolean,
    @field:Schema(description = "리뷰 개수", example = "100")
    val reviewCount: Int? = 0,
    @field:Schema(description = "영업시간", example = "10:00 ~ 17:00")
    val openingHours: String?,
) {
    constructor(schedulePlace: SchedulePlace, place: Place) : this(
        id = schedulePlace.id.getValue(),
        roomUid = place.roomUid.getValue(),
        scheduleId = schedulePlace.scheduleId.getValue(),
        name = place.name,
        url = place.url,
        placeImageUrls = place.thumbnailLinks,
        address = place.address,
        phoneNumber = place.phoneNumber,
        starGrade = place.starGrade,
        origin = place.origin,
        memo = place.memo,
        confirmed = schedulePlace.confirmed,
        reviewCount = place.reviewCount,
        openingHours = place.openingHours,
    )
}

@Schema(description = "일정 타입별 장소 그룹 응답")
data class ScheduleTypeGroupResponse(
    @field:Schema(description = "일정타입(Schedule) ID", example = "1")
    val scheduleId: Long?,
    @field:Schema(description = "일정 이름", example = "술 2차")
    val scheduleName: String,
    @field:Schema(description = "해당 일정 타입에 속한 장소 목록")
    val places: List<PlaceResponse>,
)
