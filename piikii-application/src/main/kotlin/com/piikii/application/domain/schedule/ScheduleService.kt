package com.piikii.application.domain.schedule

import com.piikii.application.port.input.ScheduleUseCase
import com.piikii.application.port.input.dto.request.RegisterSchedulesRequest
import com.piikii.application.port.input.dto.response.SchedulesResponse
import com.piikii.application.port.output.persistence.ScheduleCommandPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class ScheduleService(
    private val scheduleQueryPort: ScheduleQueryPort,
    private val scheduleCommandPort: ScheduleCommandPort,
) : ScheduleUseCase {
    @Transactional
    override fun registerSchedules(
        roomUid: UUID,
        request: RegisterSchedulesRequest,
    ) {
        val schedulesToRegister = request.toDomains(roomUid)
        deleteSchedules(roomUid, schedulesToRegister)
        schedulesToRegister
            .filter { it.id == null }
            .let { newSchedules -> scheduleCommandPort.saveSchedules(newSchedules) }
        schedulesToRegister
            .filter { it.id != null }
            .let { updatedSchedules -> scheduleCommandPort.updateSchedules(updatedSchedules) }
    }

    override fun getSchedules(roomUid: UUID): SchedulesResponse {
        val schedules = scheduleQueryPort.findSchedulesByRoomUid(roomUid)
        return SchedulesResponse.from(schedules)
    }

    private fun deleteSchedules(
        roomUid: UUID,
        schedulesToRegister: List<Schedule>,
    ) {
        val schedules = scheduleQueryPort.findSchedulesByRoomUid(roomUid)
        val scheduleIdsToRegister = schedulesToRegister.map { it.id }.toSet()
        scheduleCommandPort.deleteSchedules(schedules.filter { it.id !in scheduleIdsToRegister }.map { it.id!! })
    }
}
