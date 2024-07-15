package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.place.OriginPlace
import com.piikii.application.port.output.persistence.SourcePlaceCommandPort
import com.piikii.application.port.output.persistence.SourcePlaceQueryPort
import com.piikii.output.persistence.postgresql.persistence.entity.OriginPlaceEntity
import com.piikii.output.persistence.postgresql.persistence.repository.SourcePlaceRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class SourcePlaceAdapter(
    private val sourcePlaceRepository: SourcePlaceRepository,
) : SourcePlaceCommandPort, SourcePlaceQueryPort {
    @Transactional
    override fun save(originPlace: OriginPlace): OriginPlace {
        val entity = OriginPlaceEntity.from(originPlace)
        sourcePlaceRepository.save(entity)
        return entity.toDomain()
    }

    @Transactional
    override fun update(
        originPlace: OriginPlace,
        id: Long,
    ) {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }

    override fun retrieve(id: Long): OriginPlace {
        val sourcePlaceEntity = sourcePlaceRepository.findById(id)
        if (sourcePlaceEntity.isPresent) {
            return sourcePlaceEntity.get().toDomain()
        }
        // TODO 예외 정의
        throw EntityNotFoundException()
    }

    override fun retrieveAll(ids: List<Long>): List<OriginPlace> {
        TODO("Not yet implemented")
    }
}
