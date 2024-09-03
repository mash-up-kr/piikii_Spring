package com.piikii.application.port.input

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.port.input.dto.request.RegisterSchedulesRequest
import com.piikii.application.port.input.dto.response.SchedulesResponse

interface ScheduleUseCase {
    fun registerSchedules(
        roomUid: UuidTypeId,
        request: RegisterSchedulesRequest,
    ): SchedulesResponse

    fun getSchedules(roomUid: UuidTypeId): SchedulesResponse
}
