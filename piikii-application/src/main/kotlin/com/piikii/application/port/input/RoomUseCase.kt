package com.piikii.application.port.input

import com.piikii.application.domain.model.Room
import com.piikii.application.domain.model.SourcePlace

interface RoomUseCase {
    fun save(loginId: String): Room
}
