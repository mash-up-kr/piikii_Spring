package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.roomcategory.PlaceCategory
import com.piikii.application.domain.roomcategory.RoomCategory
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
@Table(name = "room_category", schema = "piikii")
@SQLRestriction("is_deleted = false")
@DynamicUpdate
class RoomCategoryEntity(
    @Column(name = "room_id", nullable = false)
    val roomId: UUID,
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "place_category", nullable = false)
    @Enumerated(EnumType.STRING)
    val category: PlaceCategory,
    @Column(name = "sequence", nullable = false)
    val sequence: Int,
) : BaseEntity() {
    constructor(roomCategory: RoomCategory) : this(
        roomId = roomCategory.roomId,
        name = roomCategory.name,
        category = roomCategory.category,
        sequence = roomCategory.sequence,
    )

    fun toDomain(): RoomCategory {
        return RoomCategory(
            id = this.id,
            roomId = this.roomId,
            name = this.name,
            category = this.category,
            sequence = this.sequence,
        )
    }
}
