package com.piikii.application.port.input.room.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.piikii.application.domain.room.Room

@JsonInclude(JsonInclude.Include.NON_NULL)
class RoomGetResponseForm(
    private val meetingName: String,
    private val thumbnailLinks: String? = null,
    private val message: String? = null,
) {
    constructor(room: Room) : this(
        meetingName = room.meetingName!!,
        thumbnailLinks = room.thumbnailLinks?.contents,
        message = room.message,
    )
}
