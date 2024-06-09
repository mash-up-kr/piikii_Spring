package com.piikii.output.persistence.postgresql.adapter.sourceplace

import com.piikii.application.domain.sourceplace.SourcePlace
import com.piikii.application.port.output.persistence.SourcePlaceCommandPort
import com.piikii.application.port.output.persistence.SourcePlaceQueryPort
import com.piikii.output.persistence.postgresql.persistence.entity.toDomain
import com.piikii.output.persistence.postgresql.persistence.entity.toEntity
import com.piikii.output.persistence.postgresql.persistence.repository.SourcePlaceRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class SourcePlaceAdapter(
    private val sourcePlaceRepository: SourcePlaceRepository
) : SourcePlaceCommandPort, SourcePlaceQueryPort {

    @Transactional
    override fun save(sourcePlace: SourcePlace): SourcePlace {
        val entity = sourcePlace.toEntity()
        sourcePlaceRepository.save(entity)
        return entity.toDomain()
    }

    @Transactional
    override fun update(sourcePlace: SourcePlace, id: Long) {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }

    override fun retrieve(id: Long): SourcePlace {
        val sourcePlaceEntity = sourcePlaceRepository.findById(id)
        if (sourcePlaceEntity.isPresent) {
            return sourcePlaceEntity.get().toDomain()
        }
        // TODO 예외 정의
        throw EntityNotFoundException()
    }

    override fun retrieveAll(ids: List<Long>): List<SourcePlace> {
        TODO("Not yet implemented")
    }
}
