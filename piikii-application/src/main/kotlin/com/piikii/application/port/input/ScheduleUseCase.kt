package com.piikii.application.port.input

import com.piikii.application.port.input.dto.request.RegisterSchedulesRequest
import com.piikii.application.port.input.dto.response.SchedulesResponse
import java.util.UUID

interface ScheduleUseCase {
    fun registerSchedules(
        roomUid: UUID,
        request: RegisterSchedulesRequest,
    )

    fun getSchedules(roomUid: UUID): SchedulesResponse
}
