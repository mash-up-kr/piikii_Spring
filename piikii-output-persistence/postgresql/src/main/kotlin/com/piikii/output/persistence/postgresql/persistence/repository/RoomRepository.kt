package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.output.persistence.postgresql.persistence.entity.RoomEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository : JpaRepository<RoomEntity, LongTypeId> {
    fun findByroomUid(roomUid: UuidTypeId): RoomEntity?

    fun deleteByroomUid(roomUid: UuidTypeId)
}
