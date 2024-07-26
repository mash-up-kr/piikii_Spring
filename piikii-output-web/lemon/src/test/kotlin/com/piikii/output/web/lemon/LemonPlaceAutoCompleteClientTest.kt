package com.piikii.output.web.lemon

import com.piikii.application.domain.place.OriginMapId
import com.piikii.output.web.lemon.adapter.LemonPlaceAutoCompleteClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@Disabled
@SpringBootTest
@ActiveProfiles(profiles = ["test", "local"])
@ContextConfiguration(classes = [TestConfiguration::class])
class LemonPlaceAutoCompleteClientTest {
    @Autowired
    lateinit var lemonPlaceAutoCompleteClient: LemonPlaceAutoCompleteClient

    @Test
    fun isAutoCompleteSupportedUrlTest() {
        val url = "URL을 입력해주세요"

        assertThat(lemonPlaceAutoCompleteClient.isAutoCompleteSupportedUrl(url)).isNotNull()
    }

    @Test
    fun getAutoCompletedPlaceTest() {
        val url = "URL을 입력해주세요"
        val id = 123L

        val originPlace = lemonPlaceAutoCompleteClient.getAutoCompletedPlace(url, OriginMapId(id))
        println("originPlace = $originPlace")
    }
}
