package mashup.mmz.output.eventbroker.kafka.user

import mashup.mmz.application.port.output.eventbroker.Event
import mashup.mmz.application.port.output.eventbroker.UserConsumer
import org.springframework.stereotype.Component

@Component
class UserKafkaConsumer : UserConsumer {

    override fun execute(): Event {
        TODO("Not yet implemented")
    }

}