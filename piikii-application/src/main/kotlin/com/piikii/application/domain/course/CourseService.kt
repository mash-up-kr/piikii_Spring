package com.piikii.application.domain.course

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.place.SchedulePlace
import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.port.input.CourseUseCase
import com.piikii.application.port.input.dto.response.CourseResponse
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.application.port.output.persistence.SchedulePlaceCommandPort
import com.piikii.application.port.output.persistence.SchedulePlaceQueryPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import com.piikii.application.port.output.persistence.VoteQueryPort
import com.piikii.application.port.output.web.NavigationPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CourseService(
    private val roomQueryPort: RoomQueryPort,
    private val scheduleQueryPort: ScheduleQueryPort,
    private val placeQueryPort: PlaceQueryPort,
    private val schedulePlaceQueryPort: SchedulePlaceQueryPort,
    private val schedulePlaceCommandPort: SchedulePlaceCommandPort,
    private val voteQueryPort: VoteQueryPort,
    private val navigationPort: NavigationPort,
) : CourseUseCase {
    override fun isCourseExist(roomUid: UuidTypeId): Boolean {
        val scheduleIds = scheduleQueryPort.findAllByRoomUid(roomUid)
        val confirmedSchedulePlaces = schedulePlaceQueryPort.findAllConfirmedByRoomId(roomUid)
        return scheduleIds.size == confirmedSchedulePlaces.size
    }

    @Transactional
    override fun retrieveCourse(roomUid: UuidTypeId): CourseResponse {
        val room = roomQueryPort.findById(roomUid)

        if (room.isNotVoteExpired()) {
            throw PiikiiException(
                exceptionCode = ExceptionCode.ACCESS_DENIED,
                detailMessage = VOTE_NOT_END,
            )
        }

        val schedules = scheduleQueryPort.findAllByRoomUid(roomUid)
        val placeById = placeQueryPort.findAllByRoomUid(roomUid).associateBy { it.id }
        val schedulePlaces = schedulePlaceQueryPort.findAllByRoomUid(roomUid)

        val schedulePlaceIds = schedulePlaces.map { it.id }
        val votes = voteQueryPort.findAllBySchedulePlaceIds(schedulePlaceIds)
        val agreeCountBySchedulePlaceId = voteQueryPort.findAgreeCountBySchedulePlaceId(votes)

        return CourseResponse.from(
            room = room,
            placeBySchedule = getPlaceBySchedule(schedules, placeById, schedulePlaces, agreeCountBySchedulePlaceId),
        )
    }

    @Transactional
    override fun updateCoursePlace(
        roomUid: UuidTypeId,
        placeId: LongTypeId,
    ) {
        val schedulePlace = schedulePlaceQueryPort.findById(placeId)

        if (schedulePlace.isInvalidRoomUid(roomUid)) {
            throw PiikiiException(
                exceptionCode = ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION,
                detailMessage = "Room UUID: $roomUid, Room UUID in Place: ${schedulePlace.roomUid}",
            )
        }

        schedulePlaceQueryPort.findConfirmedByScheduleId(schedulePlace.scheduleId)?.let { confirmedPlace ->
            schedulePlaceCommandPort.update(confirmedPlace.id, confirmedPlace.copy(confirmed = false))
        }

        schedulePlaceCommandPort.update(placeId, schedulePlace.copy(confirmed = true))
    }

    private fun getPlaceBySchedule(
        schedules: List<Schedule>,
        placeById: Map<LongTypeId, Place>,
        schedulePlaces: List<SchedulePlace>,
        agreeCountBySchedulePlaceId: Map<Long, Int>,
    ): Map<Schedule, CoursePlace> {
        // initial 값 설정: null과 빈 Map의 쌍으로 초기화
        val initial: Map<Schedule, CoursePlace> = emptyMap()

        return mapPlacesBySchedule(schedules, schedulePlaces)
            .entries.fold(initial) { prePlaceBySchedule, (schedule, schedulePlacesIn) ->
                // 현재 CoursePlace 생성
                val confirmedPlace =
                    getConfirmedPlace(schedule, placeById, schedulePlacesIn, agreeCountBySchedulePlaceId)

                if (confirmedPlace != null) {
                    val preCoursePlace = prePlaceBySchedule.values.lastOrNull()
                    val curCoursePlace = getCoursePlace(schedule, preCoursePlace, confirmedPlace)

                    // currentCoursePlace를 누적된 placeBySchedule 맵에 추가
                    prePlaceBySchedule + (schedule to curCoursePlace)
                } else {
                    // confirmedPlace가 null인 경우 기존 맵을 그대로 반환
                    prePlaceBySchedule
                }
            }
    }

    private fun mapPlacesBySchedule(
        schedules: List<Schedule>,
        schedulePlaces: List<SchedulePlace>,
    ): Map<Schedule, List<SchedulePlace>> {
        val schedulePlaceByScheduleId = schedulePlaces.groupBy { it.scheduleId }

        return schedules.associateWith { schedule ->
            schedulePlaceByScheduleId[schedule.id]
                ?: throw PiikiiException(
                    exceptionCode = ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION,
                    detailMessage = "$EMPTY_CONFIRMED_PLACE Schedule ID: ${schedule.id}",
                )
        }
    }

    private fun getCoursePlace(
        schedule: Schedule,
        preCoursePlace: CoursePlace?,
        confirmedPlace: Place,
    ): CoursePlace {
        val coordinate = confirmedPlace.getCoordinate()

        return CoursePlace.from(
            schedule = schedule,
            place = confirmedPlace,
            coordinate = coordinate,
            distance =
                preCoursePlace?.coordinate?.let { preCoordinate ->
                    coordinate.let { coordinate ->
                        navigationPort.getDistance(start = preCoordinate, end = coordinate)
                    }
                },
        )
    }

    private fun getConfirmedPlace(
        schedule: Schedule,
        placeById: Map<LongTypeId, Place>,
        schedulePlaces: List<SchedulePlace>,
        agreeCountBySchedulePlaceId: Map<Long, Int>,
    ): Place? {
        val confirmedPlaces = schedulePlaces.filter { it.confirmed }

        if (confirmedPlaces.size > 1) {
            throw PiikiiException(
                exceptionCode = ExceptionCode.CONFLICT,
                detailMessage = "$MULTIPLE_CONFIRMED_PLACE Schedule ID: ${schedule.id}",
            )
        }

        return confirmedPlaces.firstOrNull()
            ?.let { findPlaceInPlaceById(it.placeId, placeById) }
            ?: confirmSchedulePlace(schedulePlaces, agreeCountBySchedulePlaceId)
                ?.let { findPlaceInPlaceById(it.placeId, placeById) }
    }

    private fun findPlaceInPlaceById(
        targetPlaceId: LongTypeId,
        placeById: Map<LongTypeId, Place>,
    ): Place {
        return placeById[targetPlaceId]
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "Place not found for Place ID: $targetPlaceId",
            )
    }

    private fun confirmSchedulePlace(
        schedulePlaces: List<SchedulePlace>,
        agreeCountBySchedulePlaceId: Map<Long, Int>,
    ): SchedulePlace? {
        return schedulePlaces
            .mapNotNull { schedulePlace ->
                // place.id에 해당하는 agree count가 존재하면 schedulePlace와 count를 페어로 매핑
                agreeCountBySchedulePlaceId[schedulePlace.id.getValue()]?.let { count -> schedulePlace to count }
            }
            // agree count 내림차순 정렬, (count 동일)schedulePlace.id 오름차순 정렬
            .maxWithOrNull(compareBy({ it.second }, { -it.first.id.getValue() }))
            ?.let { (selectedSchedulePlace, _) ->
                // 최다 찬성 득표 수 schedulePlace: confirmed 상태로 변경
                schedulePlaceCommandPort.update(
                    targetId = selectedSchedulePlace.id,
                    schedulePlace = selectedSchedulePlace.copy(confirmed = true),
                )
                selectedSchedulePlace
            }
    }

    companion object {
        const val VOTE_NOT_END = "투표가 진행 중입니다."
        const val MULTIPLE_CONFIRMED_PLACE = "코스로 결정된 장소가 2개 이상입니다. 데이터를 확인해주세요."
        const val EMPTY_CONFIRMED_PLACE = "코스 후보 장소가 없습니다. 데이터를 확인해주세요."
    }
}
