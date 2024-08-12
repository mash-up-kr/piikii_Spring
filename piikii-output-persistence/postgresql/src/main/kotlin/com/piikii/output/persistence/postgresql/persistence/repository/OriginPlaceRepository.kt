package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.application.domain.place.OriginMapId
import com.piikii.output.persistence.postgresql.persistence.entity.OriginPlaceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OriginPlaceRepository : JpaRepository<OriginPlaceEntity, Long> {
    fun findByOriginMapId(originMapId: OriginMapId): OriginPlaceEntity?
}
