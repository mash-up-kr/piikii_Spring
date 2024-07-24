package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.place.OriginPlace
import com.piikii.application.port.output.persistence.OriginPlaceCommandPort
import com.piikii.application.port.output.persistence.OriginPlaceQueryPort
import com.piikii.output.persistence.postgresql.persistence.entity.OriginPlaceEntity
import com.piikii.output.persistence.postgresql.persistence.repository.OriginPlaceRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class OriginPlaceAdapter(
    private val originPlaceRepository: OriginPlaceRepository,
) : OriginPlaceCommandPort, OriginPlaceQueryPort {
    @Transactional
    override fun save(originPlace: OriginPlace): OriginPlace {
        val entity = OriginPlaceEntity.from(originPlace)
        originPlaceRepository.save(entity)
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
        val originPlaceEntity = originPlaceRepository.findById(id)
        if (originPlaceEntity.isPresent) {
            return originPlaceEntity.get().toDomain()
        }
        // TODO 예외 정의
        throw EntityNotFoundException()
    }

    override fun retrieveAll(ids: List<Long>): List<OriginPlace> {
        TODO("Not yet implemented")
    }
}
