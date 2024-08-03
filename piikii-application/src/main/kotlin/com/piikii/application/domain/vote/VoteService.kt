package com.piikii.application.domain.vote

import com.piikii.application.port.input.VoteUseCase
import com.piikii.application.port.input.dto.response.VotePlaceResponse
import com.piikii.application.port.input.dto.response.VoteResultByScheduleResponse
import com.piikii.application.port.input.dto.response.VoteResultResponse
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import com.piikii.application.port.output.persistence.VoteCommandPort
import com.piikii.application.port.output.persistence.VoteQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class VoteService(
    private val voteQueryPort: VoteQueryPort,
    private val voteCommandPort: VoteCommandPort,
    private val roomQueryPort: RoomQueryPort,
    private val placeQueryPort: PlaceQueryPort,
    private val scheduleQueryPort: ScheduleQueryPort,
) : VoteUseCase {
    override fun vote(
        roomUid: UUID,
        votes: List<Vote>,
    ) {
        val room = roomQueryPort.findById(roomUid)
        require(!room.isVoteUnavailable()) {
            throw PiikiiException(
                exceptionCode = ExceptionCode.ACCESS_DENIED,
                detailMessage = VOTE_UNAVAILABLE,
            )
        }

        val placeIds = votes.map { it.placeId }
        val placesOfRoom = placeQueryPort.findAllByPlaceIds(placeIds).filter { it.roomUid == roomUid }
        require(placesOfRoom.count() == votes.size) {
            throw PiikiiException(exceptionCode = ExceptionCode.VOTE_PLACE_ID_INVALID)
        }

        voteCommandPort.save(votes)
    }

    override fun isVoteFinished(roomUid: UUID): Boolean {
        val voteDeadline =
            roomQueryPort.findById(roomUid).voteDeadline
                ?: throw PiikiiException(
                    exceptionCode = ExceptionCode.ACCESS_DENIED,
                    detailMessage = VOTE_NOT_STARTED,
                )
        return voteDeadline.isBefore(LocalDateTime.now())
    }

    override fun getVoteResultOfRoom(roomUid: UUID): VoteResultResponse {
        val places = placeQueryPort.findAllByRoomUid(roomUid)
        val placeIds = places.map { it.id }

        val placeByScheduleId = places.groupBy { it.scheduleId }
        val scheduleById = scheduleQueryPort.findAllByRoomUid(roomUid).associateBy { it.id!! }
        val agreeCountByPlaceId =
            voteQueryPort.findAllByPlaceIds(placeIds)
                .filter { it.result == VoteResult.AGREE }
                .groupingBy { it.placeId }
                .eachCount()

        val voteResultByScheduleResponses =
            scheduleById.map { (scheduleId, schedule) ->
                val placesOfSchedule = placeByScheduleId[scheduleId] ?: emptyList()
                val votePlaceResponses =
                    placesOfSchedule.map {
                        val countOfAgree = agreeCountByPlaceId.getOrDefault(it.id, 0)
                        VotePlaceResponse(it, countOfAgree)
                    }
                VoteResultByScheduleResponse(
                    scheduleId = scheduleId,
                    scheduleName = schedule.name,
                    places = votePlaceResponses.sortedByDescending { it.countOfAgree },
                )
            }
        return VoteResultResponse(voteResultByScheduleResponses)
    }

    companion object {
        const val VOTE_UNAVAILABLE = "투표가 시작되지 않았거나, 마감되었습니다"
        const val VOTE_NOT_STARTED = "투표가 시작되지 않았습니다"
    }
}
