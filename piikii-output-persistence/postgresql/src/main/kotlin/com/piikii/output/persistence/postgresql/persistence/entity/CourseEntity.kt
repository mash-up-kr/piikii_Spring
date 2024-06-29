package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "course", schema = "piikii")
@SQLRestriction("is_deleted = false")
@DynamicUpdate
class CourseEntity(
    @Column(name = "room_id", nullable = false)
    val roomId: Long,
    @Column(name = "room_category_id", nullable = false, unique = true)
    val roomCategoryId: Long,
    @Column(name = "room_place_id", nullable = false)
    val roomPlaceId: Long,
) : BaseEntity()
