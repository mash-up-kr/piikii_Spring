package com.piikii.application.domain.room

import com.piikii.application.port.input.room.RoomUseCase
import com.piikii.application.port.input.room.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.room.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.room.dto.request.VoteGenerateRequestForm
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
    override fun make(request: RoomSaveRequestForm): RoomSaveResponseForm {
        val savedRoom = roomCommandPort.save(request.toDomain())
        // TODO: 연관관계 추가
        return RoomSaveResponseForm(savedRoom)
    }

    override fun modify(
        request: RoomUpdateRequestForm,
        roomId: Long,
    ) {
        roomCommandPort.update(request.toDomain(), roomId)
    }

    override fun remove(roomId: Long) {
        roomCommandPort.delete(roomId)
        // TODO: 연관관계 추가
    }

    override fun search(roomId: UUID): RoomGetResponseForm {
        val retrievedRoom = roomQueryPort.retrieve(roomId)
        return RoomGetResponseForm(retrievedRoom)
    }

    override fun generateVote(request: VoteGenerateRequestForm) {
        roomCommandPort.updateVoteDeadline(request.toDomain())
    }
}
