package com.piikii.output.persistence.postgresql.adapter.room

import com.piikii.application.domain.model.room.Room
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.output.persistence.postgresql.persistence.repository.RoomRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class RoomQueryAdapter(
    private val roomRepository: RoomRepository
) : RoomQueryPort {

    override fun retrieve(id: Long): Room {
        TODO("Not yet implemented")
    }

    override fun retrieveAll(ids: List<Long>): List<Room> {
        TODO("Not yet implemented")
    }
}
