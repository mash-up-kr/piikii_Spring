package com.piikii.bootstrap.controller

import com.piikii.bootstrap.AcceptanceTestHelper
import com.piikii.bootstrap.actor.MockMvcActor
import com.piikii.bootstrap.asserter.MockMvcAsserter
import com.piikii.bootstrap.fixture.dto.room.getNotValidSaveRequestFixture
import com.piikii.bootstrap.fixture.dto.room.getSaveRequestFixture
import com.piikii.bootstrap.fixture.dto.room.getSaveResponseFixture
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RoomApiTest(
    @Autowired
    mockMvcActor: MockMvcActor,
    @Autowired
    mockMvcAsserter: MockMvcAsserter,
) : AcceptanceTestHelper(mockMvcActor, mockMvcAsserter) {
    @Nested
    inner class Success {
        @Test
        fun `방 생성 성공`() {
            // Arrange && Act
            val resultActions = act(HttpMethod.POST, BASE_URL, getSaveRequestFixture())

            // Assert
            assert(
                resultActions = resultActions,
                expectedStatus = status().isCreated,
                responseDto = getSaveResponseFixture(),
            )
        }
    }

    @Nested
    inner class Fail {
        @Test
        fun `방 생성 실패 - 값 범위를 초과함`() {
            // Arrange && Act
            val resultActions = act(HttpMethod.POST, BASE_URL, getNotValidSaveRequestFixture())

            // Assert
            assert(
                resultActions = resultActions,
                expectedStatus = status().is4xxClientError,
            )
        }
    }

    companion object {
        private const val BASE_URL = "/v1/rooms"
    }
}
