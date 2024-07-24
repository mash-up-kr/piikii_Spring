package com.piikii.bootstrap.fixture.dto.room

import com.piikii.application.port.input.dto.response.SaveRoomResponse
import com.piikii.input.http.controller.dto.ResponseForm
import java.util.UUID

internal fun getSaveResponseFixture(): ResponseForm<SaveRoomResponse> {
    return ResponseForm(
        SaveRoomResponse(
            roomUid = UUID.randomUUID(),
        ),
    )
}
