package com.piikii.output.eventbroker.kafka.user

import com.piikii.application.domain.model.Event
import com.piikii.application.port.output.eventbroker.UserProducerPort
import org.springframework.stereotype.Component

@Component
class UserKafkaProducer : UserProducerPort {

    override fun execute(event: Event) {
        TODO("Not yet implemented")
    }
}
