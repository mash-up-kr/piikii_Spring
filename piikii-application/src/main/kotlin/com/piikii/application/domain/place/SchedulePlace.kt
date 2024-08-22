package com.piikii.application.domain.place

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId

data class SchedulePlace(
    val id: LongTypeId,
    val roomUid: UuidTypeId,
    val scheduleId: LongTypeId,
    val placeId: LongTypeId,
    var confirmed: Boolean,
) {
    fun isInvalidRoomUid(roomUid: UuidTypeId): Boolean {
        return this.roomUid != roomUid
    }
}
