package com.piikii.application.port.input

import com.piikii.application.port.input.dto.request.CreateSchedulesRequest
import com.piikii.application.port.input.dto.request.DeleteSchedulesRequest
import com.piikii.application.port.input.dto.response.SchedulesResponse
import java.util.UUID

interface ScheduleUseCase {
    fun createSchedules(
        roomId: UUID,
        request: CreateSchedulesRequest,
    )

    fun getSchedules(roomId: UUID): SchedulesResponse

    fun deleteSchedules(request: DeleteSchedulesRequest)
}
