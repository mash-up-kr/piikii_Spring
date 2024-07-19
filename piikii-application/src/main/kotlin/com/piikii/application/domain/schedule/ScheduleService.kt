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
        saveSchedules(schedulesToRegister)
        updateSchedules(schedulesToRegister)
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
        val schedulesToDelete = schedules.filter { it.id !in scheduleIdsToRegister }
        scheduleCommandPort.deleteSchedules(schedulesToDelete.map { it.id!! })
    }

    private fun saveSchedules(schedulesToRegister: List<Schedule>) {
        val schedulesToSave = schedulesToRegister.filter { it.id == null }
        scheduleCommandPort.saveSchedules(schedulesToSave)
    }

    private fun updateSchedules(schedulesToRegister: List<Schedule>) {
        val schedulesToUpdate = schedulesToRegister.filter { it.id != null }
        scheduleCommandPort.updateSchedules(schedulesToUpdate)
    }
}
