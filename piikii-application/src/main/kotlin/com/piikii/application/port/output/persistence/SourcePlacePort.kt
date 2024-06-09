package com.piikii.application.port.output.persistence

import com.piikii.application.domain.sourceplace.SourcePlace

interface SourcePlaceQueryPort {
    fun retrieve(id: Long): SourcePlace
    fun retrieveAll(ids: List<Long>): List<SourcePlace>
}

interface SourcePlaceCommandPort {
    fun save(sourcePlace: SourcePlace): SourcePlace
    fun update(sourcePlace: SourcePlace, id: Long)
    fun delete(id: Long)
}
