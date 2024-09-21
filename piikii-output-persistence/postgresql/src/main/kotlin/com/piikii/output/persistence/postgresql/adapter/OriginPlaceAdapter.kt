package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.place.OriginMapId
import com.piikii.application.domain.place.OriginPlace
import com.piikii.application.port.output.persistence.OriginPlaceCommandPort
import com.piikii.application.port.output.persistence.OriginPlaceQueryPort
import com.piikii.output.persistence.postgresql.persistence.entity.OriginPlaceEntity
import com.piikii.output.persistence.postgresql.persistence.repository.OriginPlaceRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class OriginPlaceAdapter(
    private val originPlaceRepository: OriginPlaceRepository,
) : OriginPlaceCommandPort, OriginPlaceQueryPort {
    @Transactional
    override fun save(originPlace: OriginPlace): OriginPlace {
        val findByOriginMapId = findByOriginMapId(originPlace.originMapId)

        if (findByOriginMapId == null) {
            return originPlaceRepository.save(OriginPlaceEntity.from(originPlace)).toDomain()
        }
        return findByOriginMapId
    }

    override fun findByOriginMapId(originMapId: OriginMapId): OriginPlace? {
        return originPlaceRepository.findByOriginMapId(originMapId)?.toDomain()
    }
}
