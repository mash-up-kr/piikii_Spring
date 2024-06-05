package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.output.persistence.postgresql.persistence.entity.RoomVoteEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoomVoteRepository : JpaRepository<RoomVoteEntity, Long>
