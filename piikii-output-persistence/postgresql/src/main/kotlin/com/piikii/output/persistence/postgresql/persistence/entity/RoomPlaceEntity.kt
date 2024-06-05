package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.model.enums.Source
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import com.piikii.output.persistence.postgresql.persistence.entity.embeded.ThumbnailLink
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "room_place", schema = "piikii")
class RoomPlaceEntity(
    @Column(name = "room_id", nullable = false)
    val roomId: Long,

    @Column(name = "url", nullable = false, length = 255)
    val url: String,

    @Embedded
    val thumbnailLink: ThumbnailLink? = null,

    @Column(length = 255)
    val address: String? = null,

    @Column(name = "phone_number", length = 15)
    val phoneNumber: String? = null,

    @Column(name = "star_grade")
    val starGrade: Float? = null,

    @Enumerated(EnumType.STRING)
    val source: Source
) : BaseEntity()
