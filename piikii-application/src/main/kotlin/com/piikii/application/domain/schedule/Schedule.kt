package com.piikii.application.domain.schedule

import java.util.UUID

data class Schedule(
    val id: Long,
    val roomId: UUID,
    val name: String,
    val sequence: Int,
)
