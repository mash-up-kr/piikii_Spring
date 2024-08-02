package com.piikii.bootstrap.asserter

import com.fasterxml.jackson.databind.ObjectMapper
import com.piikii.input.http.controller.dto.ResponseForm
import org.springframework.boot.test.context.TestComponent
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@TestComponent
class MockMvcAsserter(
    private val objectMapper: ObjectMapper,
) : AcceptanceTestAsserter {
    override fun <T> execute(
        result: ResultActions,
        expectedStatus: ResultMatcher,
        responseDto: ResponseForm<T>,
    ) {
        result.andExpect(expectedStatus)
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.data").exists())

        if (responseDto.data != null) {
            val jsonData = objectMapper.writeValueAsString(responseDto.data)
            val dataMap = objectMapper.readValue(jsonData, Map::class.java)

            dataMap.forEach { (key, _) ->
                result.andExpect(jsonPath("$.data.$key").exists())
            }
        } else {
            result.andExpect(jsonPath("$.data").exists())
        }
    }

    override fun execute(
        result: ResultActions,
        expectedStatus: ResultMatcher,
    ) {
        result.andExpectAll(
            expectedStatus,
            jsonPath("$.message").exists(),
            jsonPath("$.cause").exists(),
            jsonPath("$.timestamp").exists(),
        )
    }
}
