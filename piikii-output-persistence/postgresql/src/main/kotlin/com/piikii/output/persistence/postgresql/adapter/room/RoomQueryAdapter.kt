package com.piikii.output.persistence.postgresql.adapter.room

import com.piikii.application.domain.model.Room
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.output.persistence.postgresql.persistence.repository.RoomRepository
import org.springframework.stereotype.Repository

@Repository
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
