package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.sourceplace.SourcePlace
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
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
    @Column(name = "thumbnail_links", nullable = false, length = 255)
    val thumbnailLinks: String,
    @Column(name = "address", length = 255)
    val address: String? = null,
    @Column(name = "phone_number", length = 15)
    val phoneNumber: String? = null,
    @Column(name = "star_grade")
    val starGrade: Float? = null,
    @Enumerated(EnumType.STRING)
    val source: Source,
) : BaseEntity()

fun SourcePlaceEntity.toDomain(): SourcePlace {
    return SourcePlace(
        originMapId = this.originMapId,
        url = this.url,
        thumbnailLinks = ThumbnailLinks(this.thumbnailLinks),
        address = this.address,
        phoneNumber = this.phoneNumber,
        starGrade = this.starGrade,
        source = this.source,
    )
}

fun SourcePlace.toEntity(): SourcePlaceEntity {
    return SourcePlaceEntity(
        originMapId = this.originMapId,
        url = this.url,
        thumbnailLinks = this.thumbnailLinks.contents.toString(),
        address = this.address,
        phoneNumber = this.phoneNumber,
        starGrade = this.starGrade,
        source = this.source,
    )
}
