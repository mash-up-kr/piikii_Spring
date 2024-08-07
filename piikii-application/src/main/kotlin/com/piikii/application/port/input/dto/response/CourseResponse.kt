package com.piikii.application.port.input.dto.response

import com.piikii.application.domain.course.CoursePlace
import com.piikii.application.domain.room.Room
import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.domain.schedule.ScheduleType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "코스 정보 응답")
data class CourseResponse(
    @field:Schema(description = "모임 이름", example = "강남으로 모이자")
    val roomName: String,
    @field:Schema(description = "코스 장소 정보 목록", example = "사당으로 모이자")
    val places: List<CoursePlaceResponse>,
) {
    companion object {
        fun from(
            room: Room,
            placeBySchedule: Map<Schedule, CoursePlace>,
        ): CourseResponse {
            return CourseResponse(
                roomName = room.name,
                places = placeBySchedule.map { (_, place) -> CoursePlaceResponse.from(place) },
            )
        }
    }
}

data class CoursePlaceResponse(
    @field:Schema(description = "일정 ID", example = "1")
    val scheduleId: Long,
    @field:Schema(description = "일정 유형", example = "ALCOHOL")
    val scheduleType: ScheduleType,
    @field:Schema(description = "장소 ID", example = "1")
    val placeId: Long,
    @field:Schema(description = "장소 이름", example = "소현이네 주막")
    val name: String,
    @field:Schema(description = "장소 URL", example = "https://example.com/place")
    val url: String?,
    @field:Schema(description = "장소 주소", example = "서울시 강남구 테헤란로 123")
    val address: String?,
    @field:Schema(description = "전화번호", example = "02-1234-5678")
    val phoneNumber: String?,
    @field:Schema(description = "직전 장소로부터의 거리(m)", example = "123")
    val distance: Int?,
    @field:Schema(description = "직전 장소로부터의 도보 시간(분)", example = "10")
    val time: Int?,
) {
    companion object {
        fun from(coursePlace: CoursePlace): CoursePlaceResponse {
            return CoursePlaceResponse(
                scheduleId = coursePlace.scheduleId,
                scheduleType = coursePlace.scheduleType,
                placeId = coursePlace.placeId,
                name = coursePlace.name,
                url = coursePlace.url,
                address = coursePlace.address,
                phoneNumber = coursePlace.phoneNumber,
                distance = coursePlace.distance?.totalDistanceMeter,
                time = coursePlace.distance?.totalTimeMinute,
            )
        }
    }
}
