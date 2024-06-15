package com.piikii.input.http.controller.room.dto.request

import com.piikii.application.domain.room.Room

class RoomSaveRequestForm(
    private val meetingName: String,
    private val message: String?,
    private val password: Short
) {
    fun toDomain(): Room {
        return Room(
            meetingName = this.meetingName,
            message = this.message,
            password = this.password
        )
    }
}
