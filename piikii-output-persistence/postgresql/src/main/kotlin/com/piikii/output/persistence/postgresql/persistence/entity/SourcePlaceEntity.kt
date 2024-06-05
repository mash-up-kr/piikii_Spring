package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.model.enums.Source
import com.piikii.application.domain.model.sourceplace.SourcePlace
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import com.piikii.output.persistence.postgresql.persistence.entity.embeded.ThumbnailLink
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "source_place", schema = "piikii")
@SQLRestriction("is_deleted = false")
class SourcePlaceEntity(
    @Column(name = "origin_map_id", nullable = false)
    val originMapId: Long,

    @Column(name = "url", nullable = false, length = 255)
    val url: String,

    @Embedded
    val thumbnailLink: ThumbnailLink? = null,

    @Column(name = "address", length = 255)
    val address: String? = null,

    @Column(name = "phone_number", length = 15)
    val phoneNumber: String? = null,

    @Column(name = "star_grade")
    val starGrade: Float? = null,

    @Enumerated(EnumType.STRING)
    val source: Source
) : BaseEntity()

fun SourcePlaceEntity.toDomain(sourcePlaceEntity: SourcePlaceEntity): SourcePlace {
    return SourcePlace(
        originMapId = sourcePlaceEntity.originMapId,
        url = sourcePlaceEntity.url,
        thumbnailLinks = sourcePlaceEntity.thumbnailLink?.getContent(),
        address = sourcePlaceEntity.address,
        phoneNumber = sourcePlaceEntity.phoneNumber,
        starGrade = sourcePlaceEntity.starGrade,
        source = sourcePlaceEntity.source,
    )
}

fun SourcePlace.toEntity(): SourcePlaceEntity {
    return SourcePlaceEntity(
        originMapId = this.originMapId,
        url = this.url,
        thumbnailLink = this.thumbnailLinks?.let { ThumbnailLink(it.joinToString(",")) },
        address = this.address,
        phoneNumber = this.phoneNumber,
        starGrade = this.starGrade,
        source = this.source,
    )
}
