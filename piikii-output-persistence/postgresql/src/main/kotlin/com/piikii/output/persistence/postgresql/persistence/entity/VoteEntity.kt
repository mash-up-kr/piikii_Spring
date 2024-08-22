package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.vote.Vote
import com.piikii.application.domain.vote.VoteResult
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "vote", schema = "piikii")
@SQLRestriction("is_deleted = false")
@DynamicUpdate
class VoteEntity(
    @Column(name = "user_uid", nullable = false)
    val userUid: UuidTypeId,
    @Column(name = "place_id", nullable = false)
    val schedulePlaceId: LongTypeId,
    @Enumerated(EnumType.STRING)
    @Column(name = "result", nullable = false, length = 10)
    val result: VoteResult,
) : BaseEntity() {
    fun toDomain(): Vote {
        return Vote(
            id = LongTypeId(this.id),
            userUid = this.userUid,
            schedulePlaceId = this.schedulePlaceId,
            result = this.result,
        )
    }

    companion object {
        fun from(vote: Vote): VoteEntity {
            return VoteEntity(
                userUid = vote.userUid,
                schedulePlaceId = vote.schedulePlaceId,
                result = vote.result,
            )
        }
    }
}
