package com.piikii.application.port.output.persistence

import com.piikii.application.domain.place.OriginMapId
import com.piikii.application.domain.place.OriginPlace

interface OriginPlaceQueryPort {
    fun findByOriginMapId(originMapId: OriginMapId): OriginPlace?
}

interface OriginPlaceCommandPort {
    fun save(originPlace: OriginPlace): OriginPlace
}
