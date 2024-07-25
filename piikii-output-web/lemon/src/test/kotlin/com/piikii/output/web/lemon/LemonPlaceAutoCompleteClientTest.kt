package com.piikii.output.web.lemon

import com.piikii.output.web.lemon.adapter.LemonPlaceAutoCompleteClient
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
class LemonPlaceAutoCompleteClientTest {
    @Autowired
    lateinit var lemonPlaceAutoCompleteClient: LemonPlaceAutoCompleteClient

    @Test
    fun getAutoCompletedPlaceTest() {
        val url = "URL을 입력해주세요"
        val id = "id를 입력해주세요"

        val originPlace = lemonPlaceAutoCompleteClient.getAutoCompletedPlace(url, id)
        println("originPlace = $originPlace")
    }
}
