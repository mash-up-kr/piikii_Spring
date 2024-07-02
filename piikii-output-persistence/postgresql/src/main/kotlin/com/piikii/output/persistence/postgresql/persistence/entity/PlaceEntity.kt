package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import java.util.UUID

@Entity
@Table(name = "place", schema = "piikii")
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE piikii.place SET is_deleted = true WHERE id = ?")
@DynamicUpdate
class PlaceEntity(
    @Column(name = "roomId", nullable = false, updatable = false)
    val roomId: UUID,
    @Column(name = "schedule_id", nullable = false, updatable = false)
    var scheduleId: Long,
    @Column(name = "url", length = 255)
    var url: String?,
    @Column(name = "thumbnail_links", nullable = false, length = 255)
    var thumbnailLinks: String,
    @Column(name = "address", length = 255)
    var address: String?,
    @Column(name = "phone_number", length = 15)
    var phoneNumber: String?,
    @Column(name = "star_grade", columnDefinition = "DECIMAL(2,1) DEFAULT 0.0", nullable = false)
    var starGrade: Float? = 0.0F,
    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    var source: Source,
    @Column(name = "note", length = 150, columnDefinition = "VARCHAR(150)")
    var note: String?,
    @Column(name = "vote_like_count", nullable = false)
    var voteLikeCount: Short? = 0,
    @Column(name = "vote_dislike_count", nullable = false)
    var voteDislikeCount: Short? = 0,
) : BaseEntity() {
    fun toDomain(): Place {
        return Place(
            roomId = roomId,
            id = id,
            roomId = roomId,
            scheduleId = scheduleId,
            url = url,
            thumbnailLinks = ThumbnailLinks(thumbnailLinks),
            address = address,
            phoneNumber = phoneNumber,
            starGrade = starGrade,
            source = source,
            note = note,
            voteLikeCount = voteLikeCount,
            voteDislikeCount = voteDislikeCount,
        )
    }

    fun update(place: Place) {
        url = place.url
        thumbnailLinks = place.thumbnailLinks.contents ?: ""
        address = place.address
        phoneNumber = place.phoneNumber
        starGrade = place.starGrade
        source = place.source
        note = place.note
        voteLikeCount = place.voteLikeCount
        voteDislikeCount = place.voteDislikeCount
    }

    companion object {
        fun of(
            roomId: UUID,
            scheduleId: Long,
            place: Place,
        ): PlaceEntity {
            return PlaceEntity(
                roomId = roomId,
                scheduleId = scheduleId,
                url = place.url,
                thumbnailLinks = place.thumbnailLinks.contents ?: "",
                address = place.address,
                phoneNumber = place.phoneNumber,
                starGrade = place.starGrade,
                source = place.source,
                note = place.note,
                voteLikeCount = place.voteLikeCount,
                voteDislikeCount = place.voteDislikeCount,
            )
        }
    }
}
