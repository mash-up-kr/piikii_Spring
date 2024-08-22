package com.piikii.application.domain.vote

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.port.input.VoteUseCase
import com.piikii.application.port.input.dto.response.VotePlaceResponse
import com.piikii.application.port.input.dto.response.VoteResultByScheduleResponse
import com.piikii.application.port.input.dto.response.VoteResultResponse
import com.piikii.application.port.input.dto.response.VotedPlaceResponse
import com.piikii.application.port.input.dto.response.VotedPlacesResponse
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.application.port.output.persistence.SchedulePlaceQueryPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import com.piikii.application.port.output.persistence.VoteCommandPort
import com.piikii.application.port.output.persistence.VoteQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class VoteService(
    private val voteQueryPort: VoteQueryPort,
    private val voteCommandPort: VoteCommandPort,
    private val roomQueryPort: RoomQueryPort,
    private val placeQueryPort: PlaceQueryPort,
    private val scheduleQueryPort: ScheduleQueryPort,
    private val schedulePlaceQueryPort: SchedulePlaceQueryPort,
) : VoteUseCase {
    override fun vote(
        roomUid: UuidTypeId,
        votes: List<Vote>,
    ) {
        val room = roomQueryPort.findById(roomUid)
        require(!room.isVoteUnavailable()) {
            throw PiikiiException(
                exceptionCode = ExceptionCode.ACCESS_DENIED,
                detailMessage = VOTE_UNAVAILABLE,
            )
        }

        val schedulePlaceIds = votes.map { it.schedulePlaceId }

        val placesOfRoom = schedulePlaceQueryPort.findAllByIdIn(schedulePlaceIds).filter { it.roomUid == roomUid }
        require(placesOfRoom.count() == votes.size) {
            throw PiikiiException(exceptionCode = ExceptionCode.VOTE_PLACE_ID_INVALID)
        }

        voteCommandPort.save(votes)
    }

    override fun isVoteFinished(roomUid: UuidTypeId): Boolean {
        val voteDeadline =
            roomQueryPort.findById(roomUid).voteDeadline
                ?: throw PiikiiException(
                    exceptionCode = ExceptionCode.ACCESS_DENIED,
                    detailMessage = VOTE_NOT_STARTED,
                )
        return voteDeadline.isBefore(LocalDateTime.now())
    }

    override fun getVoteResultOfRoom(roomUid: UuidTypeId): VoteResultResponse {
        val schedulePlaces = schedulePlaceQueryPort.findAllByRoomUid(roomUid)
        val scheduleById = scheduleQueryPort.findAllByRoomUid(roomUid).associateBy { it.id }
        val schedulePlaceByScheduleId = schedulePlaces.groupBy { it.scheduleId }
        val votesGroupBySchedulePlaceId =
            voteQueryPort.findAllBySchedulePlaceIds(
                schedulePlaces.map {
                    it.id
                },
            ).groupBy { it.schedulePlaceId }
        val placeById = placeQueryPort.findAllByPlaceIds(schedulePlaces.map { it.placeId }).associateBy { it.id }

        return VoteResultResponse(
            scheduleById.map { (scheduleId, schedule) ->
                val placesOfSchedule = schedulePlaceByScheduleId[scheduleId] ?: emptyList()
                val votePlaceResponses =
                    placesOfSchedule.map {
                        val (votesOfPlaceAgreeCount, votesOfPlaceDisagreeCount) =
                            getVoteCount(
                                it.id,
                                votesGroupBySchedulePlaceId,
                            )
                        VotePlaceResponse(
                            schedulePlace = it,
                            place = placeById[it.placeId]!!,
                            countOfAgree = votesOfPlaceAgreeCount,
                            countOfDisagree = votesOfPlaceDisagreeCount,
                        )
                    }
                VoteResultByScheduleResponse(
                    scheduleId = scheduleId.getValue(),
                    scheduleName = schedule.name,
                    places = votePlaceResponses.sortedByDescending { it.countOfAgree },
                )
            },
        )
    }

    override fun retrieveVotedPlaceByUser(
        roomUid: UuidTypeId,
        userUid: UuidTypeId,
    ): VotedPlacesResponse {
        val votes = voteQueryPort.findAllByUserUid(userUid)
        val placeIdByVote = votes.associateBy { it.schedulePlaceId }
        val votedSchedulePlaces = schedulePlaceQueryPort.findAllByIdIn(votes.map { it.schedulePlaceId })
        val placeById =
            placeQueryPort.findAllByPlaceIds(
                votedSchedulePlaces.map {
                    it.placeId
                },
            ).filter { it.roomUid == roomUid }.associateBy { it.id }

        return VotedPlacesResponse(
            votedSchedulePlaces.map { schedulePlace ->
                val vote =
                    placeIdByVote[schedulePlace.id]
                        ?: throw PiikiiException(
                            exceptionCode = ExceptionCode.ACCESS_DENIED,
                            detailMessage = "$VOTE_NOT_FOUND (SchedulePlace ID: ${schedulePlace.id.getValue()})",
                        )
                VotedPlaceResponse(schedulePlace, placeById[schedulePlace.placeId]!!, vote)
            },
        )
    }

    private fun getVoteCount(
        placeId: LongTypeId,
        votesGroupByPlaceId: Map<LongTypeId, List<Vote>>,
    ): Pair<Int, Int> {
        val votesOfPlace = votesGroupByPlaceId[placeId]
        val votesOfPlaceAgreeCount = votesOfPlace?.count { it.result == VoteResult.AGREE } ?: 0
        val votesOfPlaceDisagreeCount = votesOfPlace?.count { it.result == VoteResult.DISAGREE } ?: 0
        return Pair(votesOfPlaceAgreeCount, votesOfPlaceDisagreeCount)
    }

    companion object {
        const val VOTE_UNAVAILABLE = "투표가 시작되지 않았거나, 마감되었습니다"
        const val VOTE_NOT_STARTED = "투표가 시작되지 않았습니다"
        const val VOTE_NOT_FOUND = "장소에 해당하는 투표 결과가 없습니다"
    }
}
