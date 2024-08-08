package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.output.persistence.postgresql.persistence.entity.VoteEntity
import org.springframework.data.jpa.repository.JpaRepository

interface VoteRepository : JpaRepository<VoteEntity, LongTypeId> {
    fun findAllByPlaceIdIn(placeIds: Collection<LongTypeId>): List<VoteEntity>
}
