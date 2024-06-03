package com.piikii.application.domain.service

import com.piikii.application.domain.model.Event
import com.piikii.application.domain.model.Room
import com.piikii.application.domain.model.UserCreatedEvent
import com.piikii.application.port.input.RoomUseCase
import com.piikii.application.port.output.eventbroker.UserConsumerPort
import com.piikii.application.port.output.eventbroker.UserProducerPort
import com.piikii.application.port.output.persistence.RoomCommandPort
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomCommandPort: RoomCommandPort,
    private val userProducerPort: UserProducerPort,
    private val userConsumerPort: UserConsumerPort,
) : RoomUseCase {

    override fun save(loginId: String): Room {
        sophisticatedMethodByConsumer(userConsumerPort.execute())
        val room = roomCommandPort.save(Room(loginId))
        userProducerPort.execute(UserCreatedEvent())
        return Room("sfd")
    }

    private fun sophisticatedMethodByConsumer(event: Event) {
        //TODO
    }

}
