package com.piikii.application.port.output.persistence

import com.piikii.application.domain.place.OriginPlace

interface OriginPlaceQueryPort {
    fun retrieve(id: Long): OriginPlace

    fun retrieveAll(ids: List<Long>): List<OriginPlace>
}

interface OriginPlaceCommandPort {
    fun save(originPlace: OriginPlace): OriginPlace

    fun update(
        originPlace: OriginPlace,
        id: Long,
    )

    fun delete(id: Long)
}
