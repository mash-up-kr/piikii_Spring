package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.OriginPlace
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "장소 자동완성 응답")
data class PlaceAutoCompleteResponse(
    @field:Schema(description = "장소 이름", example = "도현이네 맛집")
    val name: String,
    @field:Schema(description = "장소 URL", example = "https://example.com/place")
    val url: String,
    @field:Schema(description = "장소 이미지 URL 목록")
    val placeImageUrls: ThumbnailLinks,
    @field:Schema(description = "장소 주소", example = "서울시 강남구 테헤란로 123")
    val address: String?,
    @field:Schema(description = "전화번호", example = "02-1234-5678")
    val phoneNumber: String?,
    @field:Schema(description = "별점 (0-5)", example = "4.5")
    val starGrade: Float?,
    @field:Schema(description = "리뷰 개수", example = "232")
    val reviewCount: Int,
    @field:Schema(description = "카테고리", example = "고깃집")
    val category: String?,
    @field:Schema(
        description = "장소 정보 제공처",
        example = "MANUAL",
    )
    val origin: Origin,
    @field:Schema(description = "장소 위치 경도", example = "126.9246033")
    val longitude: Double?,
    @field:Schema(description = "장소 위치 위도", example = "33.45241976")
    val latitude: Double?,
) {
    companion object {
        fun from(originPlace: OriginPlace): PlaceAutoCompleteResponse {
            return PlaceAutoCompleteResponse(
                name = originPlace.name,
                url = originPlace.url,
                placeImageUrls = originPlace.thumbnailLinks,
                address = originPlace.address,
                phoneNumber = originPlace.phoneNumber,
                starGrade = originPlace.starGrade,
                reviewCount = originPlace.reviewCount,
                category = originPlace.category,
                origin = originPlace.origin,
                longitude = originPlace.longitude,
                latitude = originPlace.latitude,
            )
        }
    }
}
