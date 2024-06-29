package com.piikii.output.persistence.postgresql.adapter.roomplace

import com.piikii.application.domain.roomplace.RoomPlace
import com.piikii.application.port.output.persistence.RoomPlaceCommandPort
import com.piikii.application.port.output.persistence.RoomPlaceQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.persistence.postgresql.persistence.entity.RoomPlaceEntity
import com.piikii.output.persistence.postgresql.persistence.repository.RoomPlaceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
@Transactional(readOnly = true)
class RoomPlaceAdapter(
    private val roomPlaceRepository: RoomPlaceRepository,
) : RoomPlaceQueryPort, RoomPlaceCommandPort {
    @Transactional
    override fun save(
        targetRoomId: UUID,
        roomPlace: RoomPlace,
    ): RoomPlace {
        return roomPlaceRepository.save(
            RoomPlaceEntity(
                targetRoomId = targetRoomId,
                roomPlace = roomPlace,
            ),
        ).toDomain()
    }

    @Transactional
    override fun update(
        targetRoomPlaceId: Long,
        roomPlace: RoomPlace,
    ) {
        val roomPlaceEntity =
            roomPlaceRepository.findByIdOrNull(targetRoomPlaceId) ?: throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "targetRoomPlaceId : $targetRoomPlaceId",
            )
        roomPlaceEntity.update(roomPlace)
    }

    @Transactional
    override fun delete(targetRoomPlaceId: Long) {
        roomPlaceRepository.findByIdOrNull(targetRoomPlaceId) ?: throw PiikiiException(
            exceptionCode = ExceptionCode.NOT_FOUNDED,
            detailMessage = "targetRoomPlaceId : $targetRoomPlaceId",
        )
        roomPlaceRepository.deleteById(targetRoomPlaceId)
    }

    override fun retrieveByRoomPlaceId(roomPlaceId: Long): RoomPlace {
        return roomPlaceRepository.findByIdOrNull(roomPlaceId)?.toDomain() ?: throw PiikiiException(
            exceptionCode = ExceptionCode.NOT_FOUNDED,
            detailMessage = "roomPlaceId : $roomPlaceId",
        )
    }

    override fun retrieveAllByRoomId(roomId: UUID): List<RoomPlace> {
        return roomPlaceRepository.findAllByRoomId(roomId).map { it.toDomain() }
    }
}
