package com.piikii.application.domain.service

import com.piikii.application.domain.model.User
import com.piikii.application.port.input.UserUsecase
import com.piikii.application.port.output.eventbroker.Event
import com.piikii.application.port.output.eventbroker.UserConsumer
import com.piikii.application.port.output.eventbroker.UserCreatedEvent
import com.piikii.application.port.output.eventbroker.UserProducer
import com.piikii.application.port.output.persistence.UserRepository
import org.springframework.stereotype.Service

@Service
class UserRestUsecase(
    private val repository: UserRepository<User, Long>,
    private val userProducer: UserProducer,
    private val userConsumer: UserConsumer,
) : UserUsecase {

    override fun save(loginId: String): User {
        sophisticatedMethodByConsumer(userConsumer.execute())
        val user = repository.save(User(loginId))
        userProducer.execute(UserCreatedEvent())
        return User("sfd")
    }

    private fun sophisticatedMethodByConsumer(event: Event) {
        //TODO
    }
}
