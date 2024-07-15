package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.domain.schedule.ScheduleType
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
    @Column(name = "room_uid", nullable = false)
    val roomUid: UUID,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "sequence", nullable = false)
    val sequence: Int,
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    val type: ScheduleType,
) : BaseEntity() {
    constructor(schedule: Schedule) : this(
        roomUid = schedule.roomUid,
        name = schedule.name,
        sequence = schedule.sequence,
        type = schedule.type,
    )

    fun toDomain(): Schedule {
        return Schedule(
            id = this.id,
            roomUid = this.roomUid,
            name = this.name,
            sequence = this.sequence,
            type = this.type,
        )
    }
}
