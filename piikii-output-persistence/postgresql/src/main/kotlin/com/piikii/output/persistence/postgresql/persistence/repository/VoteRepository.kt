package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.output.persistence.postgresql.persistence.entity.VoteEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface VoteRepository : JpaRepository<VoteEntity, Long> {
    fun findAllBySchedulePlaceIdIn(schedulePlaceIds: Collection<Long>): List<VoteEntity>

    fun findAllByUserUid(userUid: UUID): List<VoteEntity>
}
