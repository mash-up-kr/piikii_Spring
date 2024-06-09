package com.piikii.application.domain.sourceplace

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.port.input.SourcePlaceUseCase
import com.piikii.application.port.output.persistence.SourcePlaceCommandPort
import com.piikii.application.port.output.persistence.SourcePlaceQueryPort
import org.springframework.stereotype.Service

@Service
class SourcePlaceService(
    private val sourcePlaceCommandPort: SourcePlaceCommandPort,
    private val sourcePlaceQueryPort: SourcePlaceQueryPort
) : SourcePlaceUseCase {

    override fun save(): SourcePlace {
        val test = mutableListOf(
            "https://k-diger.github.io",
            "https://k-diger.github.io",
            "https://k-diger.github.io"
        )

        val save = sourcePlaceCommandPort.save(
            SourcePlace(
                originMapId = 1L,
                url = "https://k-diger.github.io",
                thumbnailLinks = ThumbnailLinks(test),
                address = "화성시",
                phoneNumber = "010-1234-5678",
                starGrade = 5F,
                source = Source.MANUAL
            )
        )
        return save
    }

    override fun retrieve(): SourcePlace {
        return sourcePlaceQueryPort.retrieve(1L)
    }
}
