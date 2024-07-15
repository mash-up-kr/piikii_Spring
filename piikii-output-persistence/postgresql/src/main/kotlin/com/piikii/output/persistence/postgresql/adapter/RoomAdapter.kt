package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.room.Room
import com.piikii.application.port.output.persistence.RoomCommandPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.persistence.postgresql.persistence.entity.RoomEntity
import com.piikii.output.persistence.postgresql.persistence.repository.RoomRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
@Transactional(readOnly = true)
class RoomAdapter(
    private val roomRepository: RoomRepository,
) : RoomCommandPort, RoomQueryPort {
    @Transactional
    override fun save(room: Room): Room {
        val savedRoom = roomRepository.save(RoomEntity.from(room))
        return savedRoom.toDomain()
    }

    @Transactional
    override fun update(room: Room) {
        val foundRoom = findByroomUid(room.roomUid)
        foundRoom.update(room)
    }

    @Transactional
    override fun delete(roomUid: UUID) {
        roomRepository.deleteByroomUid(roomUid)
    }

    override fun findById(roomUid: UUID): Room {
        return findByroomUid(roomUid).toDomain()
    }

    private fun findByroomUid(roomUid: UUID): RoomEntity {
        return roomRepository.findByroomUid(roomUid)
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "roomUid: $roomUid",
            )
    }
}
