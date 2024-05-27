package com.piikii.output.eventbroker.kafka.user

import com.piikii.application.port.output.eventbroker.Event
import com.piikii.application.port.output.eventbroker.UserProducer
import org.springframework.stereotype.Component

@Component
class UserKafkaProducer : UserProducer {

    override fun execute(event: Event) {
        TODO("Not yet implemented")
    }
}
