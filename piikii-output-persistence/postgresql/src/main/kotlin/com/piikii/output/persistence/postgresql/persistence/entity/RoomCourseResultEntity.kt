package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.roomcourresult.RoomCourseResult
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "room_course_result", schema = "piikii")
@SQLRestriction("is_deleted = false")
@DynamicUpdate
class RoomCourseResultEntity(
    @Column(name = "room_id", nullable = false)
    val roomId: Long,
    @Column(name = "course_category_id", nullable = false)
    val courseCategoryId: Long,
    @Column(name = "room_place_id", nullable = false)
    val roomPlaceId: Long,
) : BaseEntity()

fun RoomCourseResultEntity.toDomain(): RoomCourseResult {
    return RoomCourseResult(
        roomId = this.roomId,
        courseCategoryId = this.courseCategoryId,
        roomPlaceId = this.roomPlaceId,
    )
}

fun RoomCourseResult.toEntity(): RoomCourseResultEntity {
    return RoomCourseResultEntity(
        roomId = this.roomId,
        courseCategoryId = this.courseCategoryId,
        roomPlaceId = this.roomPlaceId,
    )
}
