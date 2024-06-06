package com.piikii.application.port.input

import com.piikii.application.domain.model.room.Room

interface RoomUseCase {
    fun save(loginId: String): Room
}
