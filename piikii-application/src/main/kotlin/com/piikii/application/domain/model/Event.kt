package com.piikii.application.domain.model

fun interface Event {
    fun getTopic(): Event
}

class UserCreatedEvent : Event {
    override fun getTopic(): Event {
        TODO("Not yet implemented")
    }
}

class UserUpdatedEvent : Event {
    override fun getTopic(): Event {
        TODO("Not yet implemented")
    }
}
