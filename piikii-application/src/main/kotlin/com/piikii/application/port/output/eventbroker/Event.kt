package com.piikii.application.port.output.eventbroker

interface Event {

    fun getTopic(): Event
}
