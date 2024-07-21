package com.piikii.output.web.avocado.parser

import com.piikii.output.web.avocado.TestConfiguration
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

    @Test
    fun sharePlaceIdParserTest() {
        val parse = sharePlaceIdParser.parse("주소를 입력하세요")
        println("parse = $parse")
    }

    @Test
    fun mapPlaceIdParserTest() {
        val parse = mapPlaceIdParser.parse("주소를 입력하세요")
        println("parse = $parse")
    }
}
