package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.port.output.persistence.ScheduleCommandPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.persistence.postgresql.persistence.entity.ScheduleEntity
import com.piikii.output.persistence.postgresql.persistence.repository.PlaceRepository
import com.piikii.output.persistence.postgresql.persistence.repository.ScheduleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class ScheduleAdapter(
    private val scheduleRepository: ScheduleRepository,
    private val placeRepository: PlaceRepository,
) : ScheduleCommandPort, ScheduleQueryPort {
    @Transactional
    override fun saveSchedules(schedules: List<Schedule>) {
        scheduleRepository.saveAll(
            schedules.map { ScheduleEntity(it) },
        )
    }

    @Transactional
    override fun deleteSchedules(scheduleIds: List<LongTypeId>) {
        placeRepository.deleteByScheduleIdIn(scheduleIds.map { it.getValue() })
        scheduleRepository.deleteAll(
            scheduleIds.map { findScheduleEntityById(it) },
        )
    }

    @Transactional
    override fun updateSchedules(schedules: List<Schedule>) {
        for (schedule in schedules) {
            updateSchedule(schedule)
        }
    }

    private fun updateSchedule(schedule: Schedule) {
        val scheduleId =
            requireNotNull(schedule.id) {
                throw PiikiiException(
                    exceptionCode = ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION,
                    detailMessage = "schedule Id can't be null",
                )
            }
        val scheduleEntity = findScheduleEntityById(scheduleId)
        scheduleEntity.update(schedule)
    }

    override fun findAllByRoomUid(roomUid: UuidTypeId): List<Schedule> {
        return scheduleRepository.findByroomUidOrderBySequenceAsc(roomUid.getValue()).map { it.toDomain() }
    }

    override fun findById(id: LongTypeId): Schedule {
        return scheduleRepository.findByIdOrNull(id.getValue())?.toDomain()
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "Schedule ID: $id",
            )
    }

    override fun findAllByIds(ids: List<LongTypeId>): List<Schedule> {
        return scheduleRepository.findAllById(ids.map { it.getValue() }).map { it.toDomain() }
    }

    private fun findScheduleEntityById(id: LongTypeId): ScheduleEntity {
        return scheduleRepository.findByIdOrNull(id.getValue())
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "Schedule ID: $id",
            )
    }
}
