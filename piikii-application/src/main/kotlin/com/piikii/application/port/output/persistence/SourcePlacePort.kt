package com.piikii.application.port.output.persistence

import com.piikii.application.domain.place.OriginPlace

interface SourcePlaceQueryPort {
    fun retrieve(id: Long): OriginPlace

    fun retrieveAll(ids: List<Long>): List<OriginPlace>
}

interface SourcePlaceCommandPort {
    fun save(originPlace: OriginPlace): OriginPlace

    fun update(
        originPlace: OriginPlace,
        id: Long,
    )

    fun delete(id: Long)
}
