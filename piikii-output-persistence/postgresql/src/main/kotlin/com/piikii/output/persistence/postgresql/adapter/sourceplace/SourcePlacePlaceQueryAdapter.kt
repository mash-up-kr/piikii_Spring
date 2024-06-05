package com.piikii.output.persistence.postgresql.adapter.sourceplace

import com.piikii.application.domain.model.sourceplace.SourcePlace
import com.piikii.application.port.output.persistence.SourcePlaceQueryPort
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class SourcePlacePlaceQueryAdapter(
) : SourcePlaceQueryPort {

    override fun retrieve(id: Long): SourcePlace {
        TODO("Not yet implemented")
    }

    override fun retrieveAll(ids: List<Long>): List<SourcePlace> {
        TODO("Not yet implemented")
    }
}
