package com.piikii.application.domain.schedule

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId

data class Schedule(
    val id: LongTypeId,
    val roomUid: UuidTypeId,
    val name: String,
    val sequence: Int,
    val type: ScheduleType,
)
