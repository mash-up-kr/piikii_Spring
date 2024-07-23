package com.piikii.output.web.avocado.parser

import com.piikii.output.web.avocado.TestConfiguration
import com.piikii.output.web.avocado.adapter.AvocadoPlaceAutoCompleteClient
import org.assertj.core.api.Assertions.assertThat
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
class AvocadoPlaceIdParserTest {
    @Autowired
    lateinit var sharePlaceIdParser: SharePlaceIdParser

    @Autowired
    lateinit var mapPlaceIdParser: MapPlaceIdParser

    @Autowired
    lateinit var avocadoPlaceAutoCompleteClient: AvocadoPlaceAutoCompleteClient

    @Test
    fun sharePlaceIdParserTest() {
        val url = "주소를 입력하세요"

        val parse = sharePlaceIdParser.parse(url)
        println("parse = $parse")
    }

    @Test
    fun mapPlaceIdParserTest() {
        val url = "주소를 입력하세요"

        val pattern = mapPlaceIdParser.pattern()
        assertThat(pattern.matches(url)).isTrue()

        val parse = mapPlaceIdParser.parse(url)
        println("parse = $parse")
    }

    @Test
    fun getAutoCompletedPlaceTest() {
        val url = "주소를 입력하세요"

        val originPlace = avocadoPlaceAutoCompleteClient.getAutoCompletedPlace(url)
        println("originPlace = $originPlace")
    }
}
