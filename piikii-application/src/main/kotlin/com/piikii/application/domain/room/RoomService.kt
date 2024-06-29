package com.piikii.application.domain.room

import com.piikii.application.port.input.room.RoomUseCase
import com.piikii.application.port.input.room.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.room.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.room.dto.request.VoteDeadlineSetRequest
import com.piikii.application.port.input.room.dto.response.RoomGetResponseForm
import com.piikii.application.port.input.room.dto.response.RoomSaveResponseForm
import com.piikii.application.port.output.persistence.RoomCommandPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RoomService(
    private val roomCommandPort: RoomCommandPort,
    private val roomQueryPort: RoomQueryPort,
) : RoomUseCase {
    override fun generate(request: RoomSaveRequestForm): RoomSaveResponseForm {
        val savedRoom = roomCommandPort.save(request.toDomain())
        return RoomSaveResponseForm(savedRoom)
    }

    override fun modify(request: RoomUpdateRequestForm) {
        roomCommandPort.update(request.toDomain())
    }

    override fun remove(roomId: UUID) {
        roomCommandPort.delete(roomId)
    }

    override fun search(roomId: UUID): RoomGetResponseForm {
        val retrievedRoom = roomQueryPort.retrieve(roomId)
        return RoomGetResponseForm(retrievedRoom)
    }

    override fun changeVoteDeadline(request: VoteDeadlineSetRequest) {
        val foundRoom = roomQueryPort.retrieve(request.roomId)
        roomCommandPort.update(foundRoom.copy(voteDeadline = request.voteDeadline))
    }
}
