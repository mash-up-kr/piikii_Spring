package com.piikii.output.persistence.postgresql.adapter.room

import com.piikii.application.domain.room.Room
import com.piikii.application.port.output.persistence.RoomCommandPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.persistence.postgresql.persistence.entity.RoomEntity
import com.piikii.output.persistence.postgresql.persistence.entity.toDomain
import com.piikii.output.persistence.postgresql.persistence.repository.RoomRepository
import org.springframework.data.repository.findByIdOrNull
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
        val savedRoom = roomRepository.save(RoomEntity(room))
        return savedRoom.toDomain()
    }

    @Transactional
    override fun update(
        room: Room,
        id: Long,
    ) {
        val foundRoom =
            roomRepository.findByIdOrNull(id)
                ?: throw PiikiiException(
                    exceptionCode = ExceptionCode.NOT_FOUNDED,
                    detailMessage = "roomId: $id",
                )
        foundRoom.update(room)
    }

    @Transactional
    override fun delete(id: Long) {
        roomRepository.deleteById(id)
        // TODO: 연관관계 삭제 - 여기서 한 번에 or 어댑터 거쳐서
    }

    override fun updateVoteDeadline(room: Room) {
        val foundRoom = findByRoomId(room.roomId!!)
        foundRoom.updateVoteDeadline(room.voteDeadline)
    }

    override fun retrieve(id: Long): Room {
        val foundRoom =
            roomRepository.findByIdOrNull(id)
                ?: throw PiikiiException(
                    exceptionCode = ExceptionCode.NOT_FOUNDED,
                    detailMessage = "roomId : $id",
                )
        return foundRoom.toDomain()
    }

    override fun retrieveAll(ids: List<Long>): List<Room> {
        TODO("Not yet implemented")
    }

    private fun findByRoomId(roomId: UUID): RoomEntity {
        return roomRepository.findByRoomId(roomId)
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "roomId: $roomId",
            )
    }
}
