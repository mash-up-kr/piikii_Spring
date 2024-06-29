package com.piikii.application.domain.vote

import com.piikii.application.port.input.vote.VoteUseCase
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.application.port.output.persistence.VoteCommandPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class VoteService(
    private val voteCommandPort: VoteCommandPort,
    private val roomQueryPort: RoomQueryPort,
) : VoteUseCase {
    override fun vote(roomId: UUID, votes: List<Vote>) {
        if (roomQueryPort.retrieve(roomId).isUnavailableToVote()) {
            throw PiikiiException(
                exceptionCode = ExceptionCode.ACCESS_DENIED,
                detailMessage = VOTE_ACCESS_DENIED,
            )
        }
        voteCommandPort.vote(votes)
    }

    companion object {
        const val VOTE_ACCESS_DENIED = "투표가 시작되지 않았거나, 마감되었습니다."
    }
}
