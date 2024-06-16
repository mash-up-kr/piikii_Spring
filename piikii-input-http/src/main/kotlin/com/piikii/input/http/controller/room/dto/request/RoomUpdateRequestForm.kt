package com.piikii.input.http.controller.room.dto.request

import com.piikii.application.domain.room.Room

class RoomUpdateRequestForm(
    private val meetingName: String,
    private val message: String?,
) {
    fun toDomain(): Room {
        return Room(
            meetingName = this.meetingName,
            message = this.message,
        )
    }
}
