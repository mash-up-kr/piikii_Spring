package com.piikii.application.port.output.eventbroker

import com.piikii.application.domain.model.Event

interface UserProducerPort {
    fun execute(event: Event)
}
