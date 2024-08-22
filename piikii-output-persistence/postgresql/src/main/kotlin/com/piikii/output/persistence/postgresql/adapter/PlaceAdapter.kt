package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.place.SchedulePlace
import com.piikii.application.port.output.persistence.PlaceCommandPort
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.persistence.postgresql.persistence.entity.PlaceEntity
import com.piikii.output.persistence.postgresql.persistence.entity.SchedulePlaceEntity
import com.piikii.output.persistence.postgresql.persistence.repository.PlaceRepository
import com.piikii.output.persistence.postgresql.persistence.repository.SchedulePlaceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class PlaceAdapter(
    private val placeRepository: PlaceRepository,
    private val schedulePlaceRepository: SchedulePlaceRepository,
) : PlaceQueryPort, PlaceCommandPort {
    @Transactional
    override fun save(
        roomUid: UuidTypeId,
        scheduleIds: List<LongTypeId>,
        place: Place,
    ): List<SchedulePlace> {
        val savedPlaceEntity = placeRepository.save(PlaceEntity(roomUid, place))
        val placeId = LongTypeId(savedPlaceEntity.id)
        val schedulePlaceEntities = scheduleIds.map { SchedulePlaceEntity(roomUid, it, placeId) }
        return schedulePlaceRepository.saveAll(schedulePlaceEntities).map { it.toDomain() }
    }

    override fun saveAll(
        roomUid: UuidTypeId,
        scheduleIds: List<LongTypeId>,
        places: List<Place>,
    ): List<Place> {
        val placeEntities =
            places.map {
                PlaceEntity(
                    roomUid = roomUid,
                    place = it,
                )
            }
        return placeRepository.saveAll(placeEntities).map { it.toDomain() }
    }

    @Transactional
    override fun update(
        targetPlaceId: LongTypeId,
        place: Place,
    ): Place {
        val placeEntity =
            placeRepository.findByIdOrNull(targetPlaceId.getValue()) ?: throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "targetPlaceId : $targetPlaceId",
            )
        placeEntity.update(place)
        return placeEntity.toDomain()
    }

    @Transactional
    override fun delete(targetPlaceId: LongTypeId) {
        placeRepository.findByIdOrNull(targetPlaceId.getValue()) ?: throw PiikiiException(
            exceptionCode = ExceptionCode.NOT_FOUNDED,
            detailMessage = "targetPlaceId : $targetPlaceId",
        )
        placeRepository.deleteById(targetPlaceId.getValue())
    }

    override fun findByPlaceId(placeId: LongTypeId): Place {
        return placeRepository.findByIdOrNull(placeId.getValue())?.toDomain() ?: throw PiikiiException(
            exceptionCode = ExceptionCode.NOT_FOUNDED,
            detailMessage = "placeId : $placeId",
        )
    }

    override fun findAllByPlaceIds(placeIds: List<LongTypeId>): List<Place> {
        return placeRepository.findAllById(placeIds.map { it.getValue() }).map { it.toDomain() }
    }

    override fun findAllByRoomUid(roomUid: UuidTypeId): List<Place> {
        return placeRepository.findAllByRoomUid(roomUid).map { it.toDomain() }
    }
}
