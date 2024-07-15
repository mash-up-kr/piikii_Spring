package com.piikii.application.domain.room

import com.piikii.application.port.input.RoomUseCase
import com.piikii.application.port.input.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.dto.response.RoomResponse
import com.piikii.application.port.input.dto.response.SaveRoomResponse
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
    override fun create(request: RoomSaveRequestForm): SaveRoomResponse {
        return SaveRoomResponse(roomCommandPort.save(request.toDomain()).roomUid)
    }

    override fun modify(request: RoomUpdateRequestForm) {
        roomCommandPort.update(request.toDomain())
    }

    override fun remove(roomUid: UUID) {
        roomCommandPort.delete(roomUid)
    }

    override fun findById(roomUid: UUID): RoomResponse {
        val retrievedRoom = roomQueryPort.findById(roomUid)
        return RoomResponse.from(retrievedRoom)
    }

    override fun changeVoteDeadline(
        roomUid: UUID,
        password: Password,
        voteDeadline: LocalDateTime,
    ) {
        roomQueryPort.findById(roomUid).let { room ->
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
