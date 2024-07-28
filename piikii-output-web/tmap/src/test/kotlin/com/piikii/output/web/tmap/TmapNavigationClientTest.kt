package com.piikii.output.web.tmap

import com.piikii.output.web.tmap.adapter.TmapNavigationClient
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@Disabled
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = [TestConfiguration::class])
class TmapNavigationClientTest {
    @Autowired
    lateinit var tmapNavigationClient: TmapNavigationClient

    @Test
    fun getDistanceTest() {
        val startX = 126.9246033
        val startY = 33.45241976
        val endX = 126.9041895
        val endY = 33.4048969

        val distance = tmapNavigationClient.getDistanceBetweenPlaces(startX, startY, endX, endY)
        println("distance = $distance")
    }
}
