package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.SQLRestriction
import org.hibernate.annotations.Where

@Entity
@Table(name = "room_category", schema = "piikii")
@SQLRestriction("is_deleted = false")
class RoomCategoryEntity(
    @Column(name = "room_id", nullable = false)
    val roomId: Long,

    @Column(name = "name", nullable = false, length = 20)
    val name: String
) : BaseEntity()
