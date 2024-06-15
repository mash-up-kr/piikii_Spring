package com.piikii.input.http.controller.room.dto.response

import com.piikii.application.domain.room.Room

class RoomGetResponseForm(
    private val meetingName: String,
    private val thumbnailLinks: String? = null,
    private val message: String? = null,
)

fun Room.toRoomGetResponse(): RoomGetResponseForm {
    return RoomGetResponseForm(
        meetingName = this.meetingName!!,
        thumbnailLinks = this.thumbnailLinks?.contents,
        message = this.message,
    )
}
