package com.piikii.output.web.avocado

import com.piikii.application.domain.place.OriginMapId
import com.piikii.output.web.avocado.adapter.AvocadoPlaceAutoCompleteClient
import com.piikii.output.web.avocado.parser.MapPlaceIdParser
import com.piikii.output.web.avocado.parser.SharePlaceIdParser
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
class AvocadoPlaceAutoCompleteClientTest {
    @Autowired
    lateinit var sharePlaceIdParser: SharePlaceIdParser

    @Autowired
    lateinit var mapPlaceIdParser: MapPlaceIdParser

    @Autowired
    lateinit var avocadoPlaceAutoCompleteClient: AvocadoPlaceAutoCompleteClient

    @Test
    fun sharePlaceIdParserTest() {
        val url = "주소를 입력하세요"

        val parse = sharePlaceIdParser.parseOriginMapId(url)
        println("parse = $parse")
    }

    @Test
    fun mapPlaceIdParserTest() {
        val url = "주소를 입력하세요"

        val pattern = mapPlaceIdParser.pattern()
        assertThat(pattern.matches(url)).isTrue()

        val parse = mapPlaceIdParser.parseOriginMapId(url)
        println("parse = $parse")
    }

    @Test
    fun getAutoCompletedPlaceTest() {
        val url = "주소를 입력하세요"
        val id = 123L

        val originPlace = avocadoPlaceAutoCompleteClient.getAutoCompletedPlace(url, OriginMapId(id))
        println("originPlace = $originPlace")
    }
}
