package com.piikii.output.persistence.postgresql.adapter.sourceplace

import com.piikii.application.domain.model.sourceplace.SourcePlace
import com.piikii.application.port.output.persistence.SourcePlaceCommandPort
import com.piikii.output.persistence.postgresql.persistence.entity.SourcePlaceEntity
import com.piikii.output.persistence.postgresql.persistence.entity.SourcePlaceEntity.Companion.toVO
import com.piikii.output.persistence.postgresql.persistence.repository.SourcePlaceRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class SourcePlaceCommandAdapter(
    private val sourcePlaceRepository: SourcePlaceRepository
) : SourcePlaceCommandPort {

    override fun save(sourcePlace: SourcePlace): SourcePlace {
        val sourcePlaceEntity = SourcePlaceEntity.toEntity(sourcePlace)
        sourcePlaceRepository.save(sourcePlaceEntity)
        return sourcePlaceEntity.toVO(sourcePlaceEntity)
    }

    override fun update(sourcePlace: SourcePlace, id: Long) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }
}
