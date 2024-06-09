package com.piikii.application.domain.room

import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.port.input.RoomUseCase
import com.piikii.application.port.output.eventbroker.UserConsumerPort
import com.piikii.application.port.output.eventbroker.UserProducerPort
import com.piikii.application.port.output.persistence.RoomCommandPort
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class RoomService(
    private val roomCommandPort: RoomCommandPort,
    private val userProducerPort: UserProducerPort,
    private val userConsumerPort: UserConsumerPort,
) : RoomUseCase {

    override fun save(loginId: String): Room {
        return roomCommandPort.save(
            roomCommandPort.save(
                Room(
                    address = "",
                    meetDay = LocalDate.now(),
                    thumbnailLinks = ThumbnailLinks(""),
                    password = 1234,
                    voteDeadline = LocalDateTime.now()
                )
            )
        )
    }
}
