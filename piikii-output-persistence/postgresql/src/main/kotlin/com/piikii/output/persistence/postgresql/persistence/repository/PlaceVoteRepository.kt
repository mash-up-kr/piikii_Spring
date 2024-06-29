package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.output.persistence.postgresql.persistence.entity.VoteEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceVoteRepository : JpaRepository<VoteEntity, Long>
