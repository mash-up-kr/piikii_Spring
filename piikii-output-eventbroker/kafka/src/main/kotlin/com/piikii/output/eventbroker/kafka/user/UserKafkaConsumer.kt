package com.piikii.output.eventbroker.kafka.user

import com.piikii.application.port.output.eventbroker.Event
import com.piikii.application.port.output.eventbroker.UserConsumer
import org.springframework.stereotype.Component

@Component
class UserKafkaConsumer : UserConsumer {

    override fun execute(): Event {
        TODO("Not yet implemented")
    }

}
