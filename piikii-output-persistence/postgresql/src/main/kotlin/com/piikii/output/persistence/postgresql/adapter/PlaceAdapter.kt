package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.place.Place
import com.piikii.application.port.output.persistence.PlaceCommandPort
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.persistence.postgresql.persistence.entity.PlaceEntity
import com.piikii.output.persistence.postgresql.persistence.entity.ScheduleEntity
import com.piikii.output.persistence.postgresql.persistence.repository.PlaceRepository
import com.piikii.output.persistence.postgresql.persistence.repository.ScheduleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
@Transactional(readOnly = true)
class PlaceAdapter(
    private val placeRepository: PlaceRepository,
    private val scheduleRepository: ScheduleRepository,
) : PlaceQueryPort, PlaceCommandPort {
    @Transactional
    override fun save(
        targetRoomId: UUID,
        place: Place,
    ): Place {
        val placeEntity =
            PlaceEntity.of(
                place = place,
                roomId = targetRoomId,
            )
        return placeRepository.save(placeEntity).toDomain()
    }

    @Transactional
    override fun update(
        targetPlaceId: Long,
        place: Place,
    ) {
        val placeEntity =
            placeRepository.findByIdOrNull(targetPlaceId) ?: throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "targetPlaceId : $targetPlaceId",
            )
        placeEntity.update(place)
    }

    @Transactional
    override fun delete(targetPlaceId: Long) {
        placeRepository.findByIdOrNull(targetPlaceId) ?: throw PiikiiException(
            exceptionCode = ExceptionCode.NOT_FOUNDED,
            detailMessage = "targetPlaceId : $targetPlaceId",
        )
        placeRepository.deleteById(targetPlaceId)
    }

    override fun retrieveByPlaceId(placeId: Long): Place {
        val placeEntity =
            placeRepository.findByIdOrNull(placeId) ?: throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "placeId : $placeId",
            )
        return placeEntity.toDomain(
            schedule = findScheduleById(placeEntity.scheduleId).toDomain(),
        )
    }

    override fun retrieveAllByRoomId(roomId: UUID): List<Place> {
        return placeRepository.findAllByRoomId(roomId).map {
            it.toDomain(
                schedule = findScheduleById(it.scheduleId).toDomain(),
            )
        }
    }

    override fun findMostPopularPlaceByScheduleId(scheduleId: Long): Place {
        val schedule = findScheduleById(scheduleId).toDomain()
        val places = placeRepository.findAllByScheduleIdOrderByVoteLikeCountDescCreatedAtAsc(scheduleId)
        if (places.isEmpty()) {
            throw PiikiiException(
                exceptionCode = ExceptionCode.ACCESS_DENIED,
                detailMessage = "$NO_PLACE_IN_SCHEDULE ScheduleId: $scheduleId",
            )
        }
        return places[0].toDomain(schedule)
    }

    private fun findScheduleById(scheduleId: Long): ScheduleEntity {
        return scheduleRepository.findByIdOrNull(scheduleId) ?: throw PiikiiException(
            exceptionCode = ExceptionCode.NOT_FOUNDED,
            detailMessage = "ScheduleId: $scheduleId",
        )
    }

    companion object {
        const val NO_PLACE_IN_SCHEDULE = "스케줄에 후보지가 없습니다."
    }
}
