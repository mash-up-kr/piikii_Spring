package com.piikii.application.port.output.eventbroker

import com.piikii.application.domain.model.Event

interface UserConsumerPort {
    fun execute(): Event
}
