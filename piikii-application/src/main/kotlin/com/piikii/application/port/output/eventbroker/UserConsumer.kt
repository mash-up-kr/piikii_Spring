package com.piikii.application.port.output.eventbroker

interface UserConsumer {
    fun execute(): Event
}
