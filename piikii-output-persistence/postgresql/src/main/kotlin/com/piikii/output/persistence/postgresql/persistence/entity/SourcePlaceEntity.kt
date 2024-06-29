package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.SourcePlace
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
) : BaseEntity() {
    fun toDomain(): SourcePlace {
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

    companion object {
        fun from(sourcePlace: SourcePlace): SourcePlaceEntity {
            return SourcePlaceEntity(
                originMapId = sourcePlace.originMapId,
                url = sourcePlace.url,
                thumbnailLinks = sourcePlace.thumbnailLinks.contents.toString(),
                address = sourcePlace.address,
                phoneNumber = sourcePlace.phoneNumber,
                starGrade = sourcePlace.starGrade,
                source = sourcePlace.source,
            )
        }
    }
}
