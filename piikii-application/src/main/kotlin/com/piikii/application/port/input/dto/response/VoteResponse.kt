package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.place.SchedulePlace
import com.piikii.application.domain.vote.Vote
import com.piikii.application.domain.vote.VoteResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "투표 상태 응답")
data class VoteStatusResponse(
    @field:Schema(description = "투표 완료 여부", example = "true")
    val voteFinished: Boolean,
)

@Schema(description = "투표 결과 응답")
data class VoteResultResponse(
    @field:Schema(description = "일정별 투표 결과 목록")
    val result: List<VoteResultByScheduleResponse>,
)

@Schema(description = "일정별 투표 결과 응답")
data class VoteResultByScheduleResponse(
    @field:Schema(description = "일정 ID", example = "1")
    val scheduleId: Long?,
    @field:Schema(description = "일정 이름", example = "점심 식사")
    val scheduleName: String,
    @field:Schema(description = "해당 일정의 장소별 투표 결과 목록")
    val places: List<VotePlaceResponse>,
)

@Schema(description = "장소별 투표 결과 응답")
data class VotePlaceResponse(
    @field:Schema(description = "장소 ID", example = "1")
    val placeId: Long?,
    @field:Schema(description = "장소 이름", example = "궁내 최고의 김치찌개")
    val name: String,
    @field:Schema(description = "장소 URL", example = "https://example.com/restaurant")
    val url: String?,
    @field:Schema(description = "장소 이미지 URL 목록")
    val thumbnailLinks: ThumbnailLinks,
    @field:Schema(description = "주소", example = "서울시 강남구 테헤란로 123")
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
    @field:Schema(description = "메모", example = "여기 괜춘")
    val memo: String?,
    @field:Schema(description = "찬성 투표 수", example = "10")
    val countOfAgree: Int,
    @field:Schema(description = "반대 투표 수", example = "1")
    val countOfDisagree: Int,
    @field:Schema(description = "총 투표 수", example = "11")
    val countOfVote: Int,
) {
    constructor(schedulePlace: SchedulePlace, place: Place, countOfAgree: Int, countOfDisagree: Int) : this(
        placeId = schedulePlace.id.getValue(),
        name = place.name,
        url = place.url,
        thumbnailLinks = place.thumbnailLinks,
        address = place.address,
        phoneNumber = place.phoneNumber,
        starGrade = place.starGrade,
        origin = place.origin,
        memo = place.memo,
        countOfAgree = countOfAgree,
        countOfDisagree = countOfDisagree,
        countOfVote = countOfAgree + countOfDisagree,
    )
}

@Schema(description = "유저가 투표한 장소 목록 응답")
data class VotedPlacesResponse(
    @field:Schema(description = "투표한 장소 목록")
    val places: List<VotedPlaceResponse>,
)

@Schema(description = "유저가 투표한 장소 정보")
data class VotedPlaceResponse(
    @field:Schema(description = "장소 ID", example = "1")
    val placeId: Long?,
    @field:Schema(description = "장소 이름", example = "궁내 최고의 김치찌개")
    val name: String,
    @field:Schema(description = "장소 URL", example = "https://example.com/restaurant")
    val url: String?,
    @field:Schema(description = "장소 이미지 URL 목록")
    val thumbnailLinks: ThumbnailLinks,
    @field:Schema(description = "주소", example = "서울시 강남구 테헤란로 123")
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
    @field:Schema(description = "메모", example = "여기 괜춘")
    val memo: String?,
    @field:Schema(description = "투표한 상태", example = "AGREE")
    val voteResult: VoteResult,
) {
    constructor(schedulePlace: SchedulePlace, place: Place, vote: Vote) : this(
        placeId = schedulePlace.id.getValue(),
        name = place.name,
        url = place.url,
        thumbnailLinks = place.thumbnailLinks,
        address = place.address,
        phoneNumber = place.phoneNumber,
        starGrade = place.starGrade,
        origin = place.origin,
        memo = place.memo,
        voteResult = vote.result,
    )
}
