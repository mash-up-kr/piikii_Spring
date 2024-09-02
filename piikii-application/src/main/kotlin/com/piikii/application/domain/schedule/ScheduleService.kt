package com.piikii.application.domain.schedule

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.port.input.ScheduleUseCase
import com.piikii.application.port.input.dto.request.RegisterSchedulesRequest
import com.piikii.application.port.input.dto.response.SchedulesResponse
import com.piikii.application.port.output.persistence.ScheduleCommandPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ScheduleService(
    private val scheduleQueryPort: ScheduleQueryPort,
    private val scheduleCommandPort: ScheduleCommandPort,
) : ScheduleUseCase {
    @Transactional
    override fun registerSchedules(
        roomUid: UuidTypeId,
        request: RegisterSchedulesRequest,
    ): SchedulesResponse {
        val schedulesToRegister = request.toDomains(roomUid)
        deleteSchedules(roomUid, schedulesToRegister)
        val (newSchedules, changedSchedules) = schedulesToRegister.partition { it.id.getValue() == 0L }
        val savedSchedules = scheduleCommandPort.saveSchedules(newSchedules)
        val updatedSchedules = scheduleCommandPort.updateSchedules(changedSchedules)
        return SchedulesResponse.from(savedSchedules + updatedSchedules)
    }

    override fun getSchedules(roomUid: UuidTypeId): SchedulesResponse {
        val schedules = scheduleQueryPort.findAllByRoomUid(roomUid)
        return SchedulesResponse.from(schedules)
    }

    private fun deleteSchedules(
        roomUid: UuidTypeId,
        schedulesToRegister: List<Schedule>,
    ) {
        val schedules = scheduleQueryPort.findAllByRoomUid(roomUid)
        val scheduleIdsToRegister = schedulesToRegister.map { it.id }.toSet()
        val scheduleIdToDelete = schedules.filter { it.id !in scheduleIdsToRegister }.map { it.id }
        scheduleCommandPort.deleteSchedules(scheduleIdToDelete)
    }
}
