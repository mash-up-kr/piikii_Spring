package com.piikii.application.domain.room

import com.piikii.application.port.input.room.RoomUseCase
import com.piikii.application.port.input.room.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.room.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.room.dto.response.RoomGetResponseForm
import com.piikii.application.port.input.room.dto.response.RoomSaveResponseForm
import com.piikii.application.port.output.persistence.RoomCommandPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
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

    override fun changeVoteDeadline(
        roomId: UUID,
        password: Password,
        voteDeadline: LocalDateTime,
    ) {
        roomQueryPort.retrieve(roomId).let { room ->
            verifyPassword(room, password)
            roomCommandPort.update(room.copy(voteDeadline = voteDeadline))
        }
    }

    private fun verifyPassword(
        room: Room,
        password: Password,
    ) {
        require(room.isPasswordValid(password)) {
            throw PiikiiException(ExceptionCode.ROOM_PASSWORD_INVALID)
        }
    }
}
