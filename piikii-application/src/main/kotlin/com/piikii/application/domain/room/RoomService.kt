package com.piikii.application.domain.room

import com.piikii.application.domain.generic.UuidTypeId
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

@Service
class RoomService(
    private val roomCommandPort: RoomCommandPort,
    private val roomQueryPort: RoomQueryPort,
) : RoomUseCase {
    override fun create(request: RoomSaveRequestForm): SaveRoomResponse {
        return SaveRoomResponse(roomCommandPort.save(request.toDomain()).roomUid.getValue())
    }

    override fun modify(request: RoomUpdateRequestForm) {
        roomCommandPort.update(request.toDomain())
    }

    override fun remove(roomUid: UuidTypeId) {
        roomCommandPort.delete(roomUid)
    }

    override fun findById(roomUid: UuidTypeId): RoomResponse {
        val retrievedRoom = roomQueryPort.findById(roomUid)
        return RoomResponse.from(retrievedRoom)
    }

    override fun changeVoteDeadline(
        roomUid: UuidTypeId,
        voteDeadline: LocalDateTime,
    ) {
        roomQueryPort.findById(roomUid).let { room ->
            roomCommandPort.update(room.copy(voteDeadline = voteDeadline))
        }
    }

    override fun verifyPassword(
        roomUid: UuidTypeId,
        password: Password,
    ) {
        val room = roomQueryPort.findById(roomUid)
        require(room.isPasswordValid(password)) {
            throw PiikiiException(ExceptionCode.ROOM_PASSWORD_INVALID)
        }
    }
}
