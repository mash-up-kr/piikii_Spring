package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.port.output.persistence.ScheduleCommandPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.persistence.postgresql.persistence.entity.ScheduleEntity
import com.piikii.output.persistence.postgresql.persistence.repository.ScheduleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
@Transactional(readOnly = true)
class ScheduleAdapter(
    private val scheduleRepository: ScheduleRepository,
) : ScheduleCommandPort, ScheduleQueryPort {
    @Transactional
    override fun saveSchedules(schedules: List<Schedule>) {
        scheduleRepository.saveAll(
            schedules.map { ScheduleEntity(it) },
        )
    }

    @Transactional
    override fun deleteSchedules(scheduleIds: List<Long>) {
        scheduleRepository.deleteAll(
            scheduleIds.map { findScheduleById(it) },
        )
    }

    override fun findSchedulesByRoomId(roomId: UUID): List<Schedule> {
        return scheduleRepository.findByRoomIdOrderBySequenceAsc(roomId).map { it.toDomain() }
    }

    private fun findScheduleById(id: Long): ScheduleEntity {
        return scheduleRepository.findByIdOrNull(id)
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "Schedule ID: $id",
            )
    }
}
