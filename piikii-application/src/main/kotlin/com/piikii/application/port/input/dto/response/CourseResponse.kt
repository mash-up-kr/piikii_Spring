package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.place.Place
import com.piikii.application.domain.room.Room
import io.swagger.v3.oas.annotations.media.Schema

data class CourseResponse(
    @Schema(description = "모임 이름")
    val meetingName: String,
    @Schema(description = "코스 내 장소 정보 목록")
    val places: List<VoteResultPlaceResponse>,
) {
    companion object {
        fun from(
            room: Room,
            places: List<Place>,
        ): CourseResponse {
            return CourseResponse(
                meetingName = room.meetingName,
                places = places.map { VoteResultPlaceResponse(it) },
            )
        }
    }
}

data class VoteResultPlaceResponse(
    @Schema(description = "스케줄 이름")
    val scheduleName: String,
    @Schema(description = "장소 이름")
    val placeName: String,
    @Schema(description = "전화번호")
    val phoneNumber: String?,
    @Schema(description = "장소 주소")
    val address: String?,
    @Schema(description = "다음 장소까지 거리")
    val distanceToNextPlace: Int,
) {
    constructor(place: Place) : this(
        scheduleName = place.schedule!!.name,
        placeName = place.name,
        phoneNumber = place.phoneNumber,
        address = place.address,
        distanceToNextPlace = 0,
        // TODO: calculate with redis
    )
}
