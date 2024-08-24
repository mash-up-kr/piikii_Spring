package com.piikii.output.web.tmap

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.Place
import com.piikii.output.web.tmap.adapter.TmapNavigationAdapter
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import java.util.UUID

@Disabled
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = [TestConfiguration::class])
class TmapNavigationAdapterTest {
    @Autowired
    lateinit var tmapNavigationClient: TmapNavigationAdapter

    @Test
    fun getDistanceTest() {
        val startPlace =
            Place(
                id = LongTypeId(1L),
                roomUid = UuidTypeId(UUID.randomUUID()),
                scheduleId = LongTypeId(0L),
                name = "",
                url = null,
                thumbnailLinks = ThumbnailLinks(listOf()),
                address = null,
                phoneNumber = null,
                starGrade = null,
                origin = Origin.MANUAL,
                memo = null,
                confirmed = true,
                reviewCount = null,
                longitude = 126.9246033,
                latitude = 33.45241976,
                openingHours = null,
            )
        val endPlace =
            Place(
                id = LongTypeId(1L),
                roomUid = UuidTypeId(UUID.randomUUID()),
                scheduleId = LongTypeId(0L),
                name = "",
                url = null,
                thumbnailLinks = ThumbnailLinks(listOf()),
                address = null,
                phoneNumber = null,
                starGrade = null,
                origin = Origin.MANUAL,
                memo = null,
                confirmed = true,
                reviewCount = null,
                longitude = 126.9041895,
                latitude = 33.4048969,
                openingHours = null,
            )

        val distance = tmapNavigationClient.getDistance(startPlace, endPlace)
        println("distance = $distance")
    }
}
