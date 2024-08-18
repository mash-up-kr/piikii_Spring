package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.room.Password
import com.piikii.application.domain.room.Room
import com.piikii.application.port.output.persistence.RoomCommandPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.persistence.postgresql.persistence.entity.RoomEntity
import com.piikii.output.persistence.postgresql.persistence.repository.RoomRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

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
        val foundRoom = findByRoomUid(room.roomUid)
        foundRoom.update(room)
    }

    @Transactional
    override fun delete(roomUid: UuidTypeId) {
        roomRepository.deleteByroomUid(roomUid.getValue())
    }

    override fun findById(roomUid: UuidTypeId): Room {
        return findByRoomUid(roomUid).toDomain()
    }

    override fun verifyPassword(
        room: Room,
        password: Password,
    ) {
        require(room.isPasswordValid(password)) {
            throw PiikiiException(ExceptionCode.ROOM_PASSWORD_INVALID)
        }
    }

    private fun findByRoomUid(roomUid: UuidTypeId): RoomEntity {
        return roomRepository.findByroomUid(roomUid.getValue())
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "roomUid: $roomUid",
            )
    }
}
