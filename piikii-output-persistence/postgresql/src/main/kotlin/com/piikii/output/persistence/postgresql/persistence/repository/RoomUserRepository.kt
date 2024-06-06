package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.output.persistence.postgresql.persistence.entity.RoomUserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoomUserRepository : JpaRepository<RoomUserEntity, Long>
