package com.piikii.application.domain.service

import com.piikii.application.domain.model.Event
import com.piikii.application.domain.model.User
import com.piikii.application.domain.model.UserCreatedEvent
import com.piikii.application.port.input.UserUseCase
import com.piikii.application.port.output.eventbroker.UserConsumerPort
import com.piikii.application.port.output.eventbroker.UserProducerPort
import com.piikii.application.port.output.persistence.UserCommandPort
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userCommandPort: UserCommandPort,
    private val userProducerPort: UserProducerPort,
    private val userConsumerPort: UserConsumerPort,
) : UserUseCase {

    override fun save(loginId: String): User {
        sophisticatedMethodByConsumer(userConsumerPort.execute())
        val user = userCommandPort.save(User(loginId))
        userProducerPort.execute(UserCreatedEvent())
        return User("sfd")
    }

    private fun sophisticatedMethodByConsumer(event: Event) {
        //TODO
    }

}
