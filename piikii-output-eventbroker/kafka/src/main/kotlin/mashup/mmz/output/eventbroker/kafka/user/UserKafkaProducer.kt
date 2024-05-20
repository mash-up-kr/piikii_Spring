package mashup.mmz.output.eventbroker.kafka.user

import mashup.mmz.application.port.output.eventbroker.Event
import mashup.mmz.application.port.output.eventbroker.UserProducer
import org.springframework.stereotype.Component

@Component
class UserKafkaProducer : UserProducer {

    override fun execute(event: Event) {
        TODO("Not yet implemented")
    }
}