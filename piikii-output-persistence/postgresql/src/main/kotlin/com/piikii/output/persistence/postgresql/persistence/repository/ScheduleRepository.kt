package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.output.persistence.postgresql.persistence.entity.ScheduleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleRepository : JpaRepository<ScheduleEntity, LongTypeId> {
    fun findByroomUidOrderBySequenceAsc(roomUid: UuidTypeId): List<ScheduleEntity>
}
