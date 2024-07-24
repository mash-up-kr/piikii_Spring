package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLRestriction
import java.util.UUID

@Entity
@Table(name = "course", schema = "piikii")
@SQLRestriction("is_deleted = false")
@DynamicUpdate
class CourseEntity(
    @Column(name = "room_uid", nullable = false)
    val roomUid: UUID,
    @Column(name = "schedule_id", nullable = false, unique = true)
    val scheduleId: Long,
    @Column(name = "place_id", nullable = false)
    val placeId: Long,
) : BaseEntity()
