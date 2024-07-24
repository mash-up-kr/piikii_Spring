package com.piikii.application.domain.place

import com.piikii.application.domain.generic.Origin
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.port.input.OriginPlaceUseCase
import com.piikii.application.port.output.persistence.OriginPlaceCommandPort
import com.piikii.application.port.output.persistence.OriginPlaceQueryPort
import org.springframework.stereotype.Service

@Service
class OriginPlaceService(
    private val originPlaceCommandPort: OriginPlaceCommandPort,
    private val originPlaceQueryPort: OriginPlaceQueryPort,
) : OriginPlaceUseCase {
    override fun save(): OriginPlace {
        val test =
            mutableListOf(
                "https://k-diger.github.io",
                "https://k-diger.github.io",
                "https://k-diger.github.io",
            )

        val save =
            originPlaceCommandPort.save(
                OriginPlace(
                    id = null,
                    originMapId = 1L,
                    name = "test",
                    url = "https://k-diger.github.io",
                    thumbnailLinks = ThumbnailLinks(test),
                    address = "화성시",
                    phoneNumber = "010-1234-5678",
                    starGrade = 5F,
                    origin = Origin.MANUAL,
                    longitude = null,
                    latitude = null,
                    reviewCount = 7222,
                    category = null,
                ),
            )
        return save
    }

    override fun retrieve(): OriginPlace {
        return originPlaceQueryPort.retrieve(1L)
    }
}
