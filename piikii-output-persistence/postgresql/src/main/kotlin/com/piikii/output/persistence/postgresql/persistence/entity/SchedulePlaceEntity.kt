package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.place.SchedulePlace
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "schedule_place", schema = "piikii")
class SchedulePlaceEntity(
    @Column(name = "room_uid", nullable = false, updatable = false)
    val roomUid: UuidTypeId,
    @Column(name = "schedule_id", nullable = false, updatable = false)
    var scheduleId: LongTypeId,
    @Column(name = "place_id", nullable = false, updatable = false)
    var placeId: LongTypeId,
    @Column(name = "confirmed", nullable = false)
    var confirmed: Boolean = false,
): BaseEntity() {
    constructor(schedulePlace: SchedulePlace): this(
        roomUid = schedulePlace.roomUid,
        scheduleId = schedulePlace.scheduleId,
        placeId = schedulePlace.placeId
    )

    fun toDomain(): SchedulePlace {
        return SchedulePlace(
            id = LongTypeId(id),
            roomUid = roomUid,
            scheduleId = scheduleId,
            placeId = placeId,
            confirmed = confirmed,
        )
    }
}
