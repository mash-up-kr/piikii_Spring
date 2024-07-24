package com.piikii.bootstrap.fixture.dto.room

import com.piikii.application.domain.room.Password
import com.piikii.application.port.input.dto.request.RoomSaveRequestForm

internal fun getSaveRequestFixture(): RoomSaveRequestForm {
    return RoomSaveRequestForm(
        name = "방 테스트 1",
        message = "방 테스트 메세지 1",
        address = "우리집",
        thumbnailLink = "",
        password = Password(value = "0001"),
    )
}

internal fun getNotValidSaveRequestFixture(): RoomSaveRequestForm {
    return RoomSaveRequestForm(
        name = "방 테스트 1",
        message = "방 테스트 메세지 1",
        address = "우리집",
        thumbnailLink = "",
        password = Password(value = "0001010100101"),
    )
}
