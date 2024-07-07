package com.piikii.output.persistence.postgresql.persistence.repository.projection

import com.piikii.output.persistence.postgresql.persistence.entity.PlaceEntity
import com.piikii.output.persistence.postgresql.persistence.entity.ScheduleEntity

interface PlaceScheduleProjection {
    fun getPlace(): PlaceEntity

    fun getSchedule(): ScheduleEntity
}
