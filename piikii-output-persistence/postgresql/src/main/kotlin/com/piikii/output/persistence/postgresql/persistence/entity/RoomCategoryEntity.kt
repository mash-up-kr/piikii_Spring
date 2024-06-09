package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.generic.CategoryName
import com.piikii.application.domain.roomcategory.RoomCategory
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "room_category", schema = "piikii")
@SQLRestriction("is_deleted = false")
@DynamicUpdate
class RoomCategoryEntity(
    @Column(name = "room_id", nullable = false)
    val roomId: Long,

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    val name: CategoryName
) : BaseEntity()

fun RoomCategoryEntity.toDomain(): RoomCategory {
    return RoomCategory(
        roomId = this.roomId,
        name = this.name
    )
}

fun RoomCategory.toEntity(): RoomCategoryEntity {
    return RoomCategoryEntity(
        roomId = this.roomId,
        name = this.name
    )
}
