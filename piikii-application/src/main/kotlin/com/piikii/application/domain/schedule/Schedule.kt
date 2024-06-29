package com.piikii.application.domain.schedule

import java.util.UUID

class Schedule(
    val id: Long? = null,
    val roomId: UUID,
    val name: String,
    val placeType: PlaceType,
    val sequence: Int,
)
