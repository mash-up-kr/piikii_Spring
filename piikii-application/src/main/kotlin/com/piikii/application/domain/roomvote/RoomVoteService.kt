package com.piikii.application.domain.roomvote

import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class RoomVoteService(
    private val roomQueryPort: RoomQueryPort,
) {
    fun vote(roomId: UUID) {
        val room = roomQueryPort.retrieve(roomId)

        if (room.voteDeadline == null || room.voteDeadline.isBefore(LocalDateTime.now())) {
            throw PiikiiException(
                exceptionCode = ExceptionCode.ACCESS_DENIED,
                detailMessage = VOTE_IS_CLOSED,
            )
        }

        TODO("implementation additional flow")
    }

    companion object {
        const val VOTE_IS_CLOSED = "투표가 마감되었습니다."
    }
}
