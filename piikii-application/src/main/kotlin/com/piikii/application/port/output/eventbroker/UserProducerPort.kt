package com.piikii.application.port.output.eventbroker

import com.piikii.application.events.Event

interface UserProducerPort {
    fun execute(event: Event)
}
