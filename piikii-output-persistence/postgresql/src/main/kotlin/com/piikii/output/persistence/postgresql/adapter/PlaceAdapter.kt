package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.place.Place
import com.piikii.application.port.output.persistence.PlaceCommandPort
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.persistence.postgresql.persistence.entity.PlaceEntity
import com.piikii.output.persistence.postgresql.persistence.repository.PlaceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class PlaceAdapter(
    private val placeRepository: PlaceRepository,
) : PlaceQueryPort, PlaceCommandPort {
    @Transactional
    override fun save(
        roomUid: UuidTypeId,
        scheduleId: LongTypeId,
        place: Place,
    ): Place {
        val placeEntity =
            PlaceEntity(
                roomUid = roomUid,
                scheduleId = scheduleId,
                place = place,
            )
        return placeRepository.save(placeEntity).toDomain()
    }

    @Transactional
    override fun update(
        targetPlaceId: LongTypeId,
        place: Place,
    ): Place {
        val placeEntity =
            placeRepository.findByIdOrNull(targetPlaceId) ?: throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "targetPlaceId : $targetPlaceId",
            )
        placeEntity.update(place)
        return placeEntity.toDomain()
    }

    @Transactional
    override fun delete(targetPlaceId: LongTypeId) {
        placeRepository.findByIdOrNull(targetPlaceId) ?: throw PiikiiException(
            exceptionCode = ExceptionCode.NOT_FOUNDED,
            detailMessage = "targetPlaceId : $targetPlaceId",
        )
        placeRepository.deleteById(targetPlaceId)
    }

    override fun findByPlaceId(placeId: LongTypeId): Place {
        return placeRepository.findByIdOrNull(placeId)?.toDomain() ?: throw PiikiiException(
            exceptionCode = ExceptionCode.NOT_FOUNDED,
            detailMessage = "placeId : $placeId",
        )
    }

    override fun findAllByPlaceIds(placeIds: List<LongTypeId>): List<Place> {
        return placeRepository.findAllById(placeIds).map { it.toDomain() }
    }

    override fun findAllByRoomUid(roomUid: UuidTypeId): List<Place> {
        return placeRepository.findAllByRoomUid(roomUid).map { it.toDomain() }
    }

    override fun findConfirmedByScheduleId(scheduleId: LongTypeId): Place? {
        val places = placeRepository.findByScheduleIdAndConfirmed(scheduleId, true)
        return places.singleOrNull()?.toDomain()
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.ACCESS_DENIED,
                detailMessage = "중복된 장소 코스가 있습니다. 데이터를 확인하세요. Schedule ID : $scheduleId",
            )
    }
}
