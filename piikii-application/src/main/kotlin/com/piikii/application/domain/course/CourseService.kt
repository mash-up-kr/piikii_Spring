package com.piikii.application.domain.course

import com.piikii.application.domain.place.Place
import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.domain.vote.VoteResult
import com.piikii.application.port.input.CourseUseCase
import com.piikii.application.port.input.OriginPlaceUseCase
import com.piikii.application.port.input.dto.response.CourseResponse
import com.piikii.application.port.output.persistence.CourseQueryPort
import com.piikii.application.port.output.persistence.PlaceCommandPort
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import com.piikii.application.port.output.persistence.VoteQueryPort
import com.piikii.application.port.output.web.NavigationClient
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class CourseService(
    private val courseQueryPort: CourseQueryPort,
    private val roomQueryPort: RoomQueryPort,
    private val scheduleQueryPort: ScheduleQueryPort,
    private val placeQueryPort: PlaceQueryPort,
    private val placeCommandPort: PlaceCommandPort,
    private val originPlaceUseCase: OriginPlaceUseCase,
    private val voteQueryPort: VoteQueryPort,
    private val navigationClient: NavigationClient,
) : CourseUseCase {
    override fun isCourseExist(roomUid: UUID): Boolean {
        return courseQueryPort.isCourseExist(roomUid)
    }

    @Transactional
    override fun retrieveCourse(roomUid: UUID): CourseResponse {
        val room = roomQueryPort.findById(roomUid)

        if (!room.isVoteUnavailable()) {
            throw PiikiiException(
                exceptionCode = ExceptionCode.ACCESS_DENIED,
                detailMessage = VOTE_NOT_END,
            )
        }

        val schedules = scheduleQueryPort.findAllByRoomUid(roomUid)
        val places = placeQueryPort.findAllByRoomUid(roomUid)
        val agreeCountByPlaceId =
            voteQueryPort.findAllByPlaceIds(places.map { it.id })
                .filter { it.result == VoteResult.AGREE }
                .groupingBy { it.placeId }
                .eachCount()

        val initial: Pair<CoursePlace?, Map<Schedule, CoursePlace>> = null to emptyMap()
        val placeBySchedule =
            mapPlacesBySchedule(schedules, places)
                .entries.fold(initial) { acc, (schedule, places) ->
                    val (previousCoursePlace, scheduleMap) = acc
                    val currentCoursePlace = getCoursePlace(schedule, previousCoursePlace, places, agreeCountByPlaceId)
                    currentCoursePlace to scheduleMap + (schedule to currentCoursePlace)
                }.second

        return CourseResponse.from(
            room = room,
            placeBySchedule = placeBySchedule,
        )
    }

    private fun mapPlacesBySchedule(
        schedules: List<Schedule>,
        places: List<Place>,
    ): Map<Schedule, List<Place>> {
        val placesByScheduleId = places.groupBy { it.scheduleId }
        return schedules.associateWith { schedule ->
            placesByScheduleId[schedule.id]
                ?: throw PiikiiException(
                    exceptionCode = ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION,
                    detailMessage = "$EMPTY_CONFIRMED_PLACE Schedule ID: ${schedule.id}",
                )
        }
    }

    private fun getCoursePlace(
        schedule: Schedule,
        preCoursePlace: CoursePlace?,
        places: List<Place>,
        agreeCountByPlaceId: Map<Long, Int>,
    ): CoursePlace {
        val confirmedPlaces = places.filter { it.confirmed }

        if (confirmedPlaces.size > 1) {
            throw PiikiiException(
                exceptionCode = ExceptionCode.CONFLICT,
                detailMessage = "$MULTIPLE_CONFIRMED_PLACE Schedule ID: ${schedule.id}",
            )
        }

        val confirmedPlace = confirmedPlaces.firstOrNull() ?: confirmPlace(schedule, places, agreeCountByPlaceId)
        val coordinate = getCoordinate(confirmedPlace)

        return CoursePlace.from(
            schedule = schedule,
            place = confirmedPlace,
            coordinate = coordinate,
            distance = getDistance(preCoursePlace?.coordinate, coordinate),
        )
    }

    private fun getCoordinate(place: Place): Coordinate? {
        return if (place.url != null) {
            Coordinate.from(
                place = originPlaceUseCase.getAutoCompleteOriginPlace(place.url),
            )
        } else {
            null
        }
    }

    private fun getDistance(
        from: Coordinate?,
        to: Coordinate?,
    ): Distance? {
        return if (from != null && to != null) {
            navigationClient.getDistance(start = from, end = to)
        } else {
            null
        }
    }

    private fun confirmPlace(
        schedule: Schedule,
        places: List<Place>,
        agreeCountByPlaceId: Map<Long, Int>,
    ): Place {
        return places
            .mapNotNull { place ->
                agreeCountByPlaceId[place.id]?.let { count -> place to count }
            }
            .maxWithOrNull(compareBy({ it.second }, { it.first.id }))
            ?.let { (selectedPlace, _) ->
                placeCommandPort.update(
                    targetPlaceId = selectedPlace.id,
                    place = selectedPlace.copy(confirmed = true),
                )
            }
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION,
                detailMessage = "$EMPTY_CONFIRMED_PLACE Schedule ID: ${schedule.id}",
            )
    }

    companion object {
        const val VOTE_NOT_END = "투표가 진행 중입니다."
        const val MULTIPLE_CONFIRMED_PLACE = "코스로 결정된 장소가 2개 이상입니다. 데이터를 확인해주세요."
        const val EMPTY_CONFIRMED_PLACE = "코스 후보 장소가 없습니다. 데이터를 확인해주세요."
    }
}
