package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.schedule.PlaceType
import com.piikii.application.domain.schedule.Schedule
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLRestriction
import java.util.UUID

@Entity
@Table(name = "schedule", schema = "piikii")
@SQLRestriction("is_deleted = false")
@DynamicUpdate
class ScheduleEntity(
    @Column(name = "room_id", nullable = false)
    val roomId: UUID,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "place_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val placeType: PlaceType,
    @Column(name = "sequence", nullable = false)
    val sequence: Int,
) : BaseEntity() {
    constructor(schedule: Schedule) : this(
        roomId = schedule.roomId,
        name = schedule.name,
        placeType = schedule.placeType,
        sequence = schedule.sequence,
    )

    fun toDomain(): Schedule {
        return Schedule(
            id = this.id,
            roomId = this.roomId,
            name = this.name,
            placeType = this.placeType,
            sequence = this.sequence,
        )
    }
}
