package mashup.mmz.application.domain.service

import mashup.mmz.application.domain.model.User
import mashup.mmz.application.port.input.UserUsecase
import mashup.mmz.application.port.output.eventbroker.Event
import mashup.mmz.application.port.output.eventbroker.UserConsumer
import mashup.mmz.application.port.output.eventbroker.UserCreatedEvent
import mashup.mmz.application.port.output.eventbroker.UserProducer
import mashup.mmz.application.port.output.persistence.UserRepository

class UserRestUsecase(
    private val repository: UserRepository<User, Long>,
    private val userProducer: UserProducer,
    private val userConsumer: UserConsumer,
) : UserUsecase {

    override fun save(loginId: String): User {
        sophisticatedMethodByConsumer(userConsumer.execute())
        val user = repository.save(User(loginId))
        userProducer.execute(UserCreatedEvent())
        return user
    }

    private fun sophisticatedMethodByConsumer(event: Event) {
        //TODO
    }
}