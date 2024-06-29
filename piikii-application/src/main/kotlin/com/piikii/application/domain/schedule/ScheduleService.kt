package com.piikii.application.domain.schedule

import com.piikii.application.port.input.ScheduleUseCase
import com.piikii.application.port.input.dto.request.CreateSchedulesRequest
import com.piikii.application.port.input.dto.request.DeleteSchedulesRequest
import com.piikii.application.port.input.dto.response.SchedulesResponse
import com.piikii.application.port.output.persistence.ScheduleCommandPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ScheduleService(
    private val scheduleQueryPort: ScheduleQueryPort,
    private val scheduleCommandPort: ScheduleCommandPort,
) : ScheduleUseCase {
    override fun createSchedules(
        roomId: UUID,
        request: CreateSchedulesRequest,
    ) {
        scheduleCommandPort.saveSchedules(request.toDomains(roomId))
    }

    override fun getSchedules(roomId: UUID): SchedulesResponse {
        return SchedulesResponse.from(
            scheduleQueryPort.findSchedulesByRoomId(roomId),
        )
    }

    override fun deleteSchedules(request: DeleteSchedulesRequest) {
        scheduleCommandPort.deleteSchedules(request.scheduleIds)
    }
}
