package com.piikii.output.persistence.postgresql.adapter.room

import com.piikii.application.domain.room.Room
import com.piikii.application.port.output.persistence.RoomCommandPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.persistence.postgresql.persistence.entity.toDomain
import com.piikii.output.persistence.postgresql.persistence.entity.toEntity
import com.piikii.output.persistence.postgresql.persistence.repository.RoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class RoomAdapter(
    private val roomRepository: RoomRepository
) : RoomCommandPort, RoomQueryPort {

    @Transactional
    override fun save(room: Room): Room {
        val savedRoom = roomRepository.save(room.toEntity())
        return savedRoom.toDomain()
    }

    @Transactional
    override fun update(room: Room, id: Long) {
        val foundRoom = roomRepository.findByIdOrNull(id)
            ?: throw PiikiiException(ExceptionCode.NOT_FOUNDED)
        foundRoom.update(room)
    }

    @Transactional
    override fun delete(id: Long) {
        roomRepository.deleteById(id)
        //TODO: 연관관계 삭제 - 여기서 한 번에 or 어댑터 거쳐서
    }

    override fun retrieve(id: Long): Room {
        val foundRoom = roomRepository.findByIdOrNull(id)
            ?: throw PiikiiException(ExceptionCode.NOT_FOUNDED)
        return foundRoom.toDomain()
    }

    override fun retrieveAll(ids: List<Long>): List<Room> {
        TODO("Not yet implemented")
    }
}
