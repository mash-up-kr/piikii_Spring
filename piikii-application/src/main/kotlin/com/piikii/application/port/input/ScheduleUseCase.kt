package com.piikii.application.port.input

import com.piikii.application.port.input.dto.request.RegisterSchedulesRequest
import java.util.UUID

interface ScheduleUseCase {
    fun registerSchedules(
        roomUid: UUID,
        request: RegisterSchedulesRequest,
    )
}
