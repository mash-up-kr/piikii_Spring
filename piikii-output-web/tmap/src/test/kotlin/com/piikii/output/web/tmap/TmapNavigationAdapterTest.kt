package com.piikii.output.web.tmap

import com.piikii.application.domain.course.Coordinate
import com.piikii.output.web.tmap.adapter.TmapNavigationAdapter
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
class TmapNavigationAdapterTest {
    @Autowired
    lateinit var tmapNavigationClient: TmapNavigationAdapter

    @Test
    fun getDistanceTest() {
        val start = Coordinate(x = 126.9246033, y = 33.45241976)
        val end = Coordinate(x = 126.9041895, y = 33.4048969)

        val distance = tmapNavigationClient.getDistance(start, end)
        println("distance = $distance")
    }
}
