package com.piikii.application.port.output.eventbroker

interface UserProducer {
    fun execute(event: Event)
}
