package com.piikii.output.persistence.postgresql.persistence.repository

import com.piikii.output.persistence.postgresql.persistence.entity.OriginPlaceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SourcePlaceRepository : JpaRepository<OriginPlaceEntity, Long>
