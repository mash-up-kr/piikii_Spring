package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.place.SchedulePlace
import com.piikii.application.port.output.persistence.SchedulePlaceCommandPort
import com.piikii.application.port.output.persistence.SchedulePlaceQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.persistence.postgresql.persistence.entity.SchedulePlaceEntity
import com.piikii.output.persistence.postgresql.persistence.repository.SchedulePlaceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class SchedulePlaceAdapter(
    private val schedulePlaceRepository: SchedulePlaceRepository,
) : SchedulePlaceQueryPort, SchedulePlaceCommandPort {
    override fun findAllByRoomUid(roomUid: UuidTypeId): List<SchedulePlace> {
        return schedulePlaceRepository.findAllByRoomUid(roomUid).map { it.toDomain() }
    }

    override fun findAllByPlaceId(placeId: LongTypeId): List<SchedulePlace> {
        return schedulePlaceRepository.findAllByPlaceId(placeId).map { it.toDomain() }
    }

    override fun findAllConfirmedByRoomId(roomId: UuidTypeId): List<SchedulePlace> {
        return schedulePlaceRepository.findAllByRoomUidAndConfirmed(roomId, true).map { it.toDomain() }
    }

    override fun findByRoomUidAndPlaceId(
        roomUid: UuidTypeId,
        placeId: LongTypeId,
    ): SchedulePlace {
        return schedulePlaceRepository.findByRoomUidAndPlaceId(roomUid, placeId) ?.toDomain()
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "Not Found SchedulePlace: roomId : $roomUid, placeId: $placeId",
            )
    }

    override fun findConfirmedByScheduleId(scheduleId: LongTypeId): SchedulePlace? {
        return schedulePlaceRepository.findByScheduleIdAndConfirmed(scheduleId, true)?.toDomain()
    }

    override fun findAllByRoomUidAndPaceIds(
        roomUid: UuidTypeId,
        placeIds: List<LongTypeId>,
    ): List<SchedulePlace> {
        return schedulePlaceRepository.findByRoomUidAndPlaceIdIn(roomUid, placeIds.map { it.getValue() })
            .map { it.toDomain() }
    }

    override fun findById(id: LongTypeId): SchedulePlace {
        return find(id).toDomain()
    }

    override fun findAllByIdIn(ids: List<LongTypeId>): List<SchedulePlace> {
        return schedulePlaceRepository.findAllByIdIn(ids.map { it.getValue() }).map { it.toDomain() }
    }

    override fun saveAll(schedulePlaces: List<SchedulePlace>) {
        val schedulePlaceEntities = schedulePlaces.map { SchedulePlaceEntity(it) }
        schedulePlaceRepository.saveAll(schedulePlaceEntities)
    }

    override fun deleteAllByPlaceIdAndScheduleIds(
        placeId: LongTypeId,
        scheduleIds: List<LongTypeId>,
    ) {
        schedulePlaceRepository.deleteAllByPlaceIdAndScheduleIdIn(placeId, scheduleIds.map { it.getValue() })
    }

    override fun deleteAllByPlaceId(placeId: LongTypeId) {
        schedulePlaceRepository.deleteAllByPlaceId(placeId)
    }

    override fun update(
        targetId: LongTypeId,
        schedulePlace: SchedulePlace,
    ) {
        val schedulePlaceEntity = find(targetId)
        schedulePlaceEntity.update(schedulePlace)
    }

    private fun find(id: LongTypeId): SchedulePlaceEntity {
        return schedulePlaceRepository.findByIdOrNull(id.getValue()) ?: throw PiikiiException(
            exceptionCode = ExceptionCode.NOT_FOUNDED,
            detailMessage = "schedulePlaceId : $id",
        )
    }
}
