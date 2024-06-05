package com.piikii.output.eventbroker.kafka.user

import com.piikii.application.domain.model.events.Event
import com.piikii.application.port.output.eventbroker.UserConsumerPort
import org.springframework.stereotype.Component

@Component
class UserKafkaConsumer : UserConsumerPort {

    override fun execute(): Event {
        TODO("Not yet implemented")
    }

}
