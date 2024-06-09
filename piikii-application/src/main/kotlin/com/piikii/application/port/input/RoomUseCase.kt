package com.piikii.application.port.input

import com.piikii.application.domain.room.Room

interface RoomUseCase {
    fun save(loginId: String): Room?
}
