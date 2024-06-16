package com.piikii.application.domain.room

import com.piikii.application.port.input.room.RoomUseCase
import com.piikii.application.port.input.room.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.room.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.room.dto.response.RoomGetResponseForm
import com.piikii.application.port.input.room.dto.response.RoomSaveResponseForm
import com.piikii.application.port.output.eventbroker.UserConsumerPort
import com.piikii.application.port.output.eventbroker.UserProducerPort
import com.piikii.application.port.output.persistence.RoomCommandPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomCommandPort: RoomCommandPort,
    private val roomQueryPort: RoomQueryPort,
    private val userProducerPort: UserProducerPort,
    private val userConsumerPort: UserConsumerPort,
) : RoomUseCase {
    override fun makeRoom(request: RoomSaveRequestForm): RoomSaveResponseForm {
        val savedRoom = roomCommandPort.save(request.toDomain())
        // TODO: 연관관계 추가
        return RoomSaveResponseForm(savedRoom)
    }

    override fun modifyRoom(
        request: RoomUpdateRequestForm,
        roomId: Long,
    ) {
        roomCommandPort.update(request.toDomain(), roomId)
    }

    override fun removeRoom(roomId: Long) {
        roomCommandPort.delete(roomId)
        // TODO: 연관관계 추가
    }

    override fun searchRoom(roomId: Long): RoomGetResponseForm {
        val retrievedRoom = roomQueryPort.retrieve(roomId)
        return RoomGetResponseForm(retrievedRoom)
    }
}
