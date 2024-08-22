package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.output.persistence.postgresql.persistence.entity.PlaceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceRepository : JpaRepository<PlaceEntity, Long> {
    fun findAllByRoomUid(roomUid: UuidTypeId): List<PlaceEntity>

    fun deleteAllByIdIn(ids: List<Long>)
}
