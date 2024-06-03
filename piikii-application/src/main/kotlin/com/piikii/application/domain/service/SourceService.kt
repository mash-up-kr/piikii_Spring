package com.piikii.application.domain.service

import com.piikii.application.domain.model.SourcePlace
import com.piikii.application.port.input.SourcePlaceUseCase
import com.piikii.application.port.output.persistence.SourcePlaceCommandPort
import org.springframework.stereotype.Service

@Service
class SourceService(
    private val sourcePlaceCommandPort: SourcePlaceCommandPort
) : SourcePlaceUseCase {

    override fun save(): SourcePlace {
        return sourcePlaceCommandPort.save(
            SourcePlace(
                originMapId = 1L,
                url = "https://k-diger.github.io",
                thumbnailLink = mutableListOf(
                    "https://k-diger.github.io",
                    "https://k-diger.github.io",
                    "https://k-diger.github.io"
                ),
                address = "화성시",
                phoneNumber = "010-1234-5678",
                starGrade = 5F,
                source = "MANUAL"
            )
        )
    }
}
