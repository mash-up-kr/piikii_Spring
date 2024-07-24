package com.piikii.bootstrap.actor

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.test.context.TestComponent
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@TestComponent
class MockMvcActor(
    private val objectMapper: ObjectMapper,
    private val mockMvc: MockMvc,
) : AcceptanceTestActor {
    override fun execute(
        method: HttpMethod,
        uri: String,
        requestBody: Any?,
        parameters: Map<String, String>,
    ): ResultActions {
        val requestBuilder = createRequestBuilder(method, uri)
        return mockMvc.perform(configureRequest(requestBuilder, requestBody, parameters))
            .andDo(MockMvcResultHandlers.print())
    }

    private fun createRequestBuilder(
        method: HttpMethod,
        uri: String,
    ): MockHttpServletRequestBuilder =
        when (method) {
            HttpMethod.GET -> MockMvcRequestBuilders.get(uri)
            HttpMethod.POST -> MockMvcRequestBuilders.post(uri)
            HttpMethod.PUT -> MockMvcRequestBuilders.put(uri)
            HttpMethod.PATCH -> MockMvcRequestBuilders.patch(uri)
            HttpMethod.DELETE -> MockMvcRequestBuilders.delete(uri)
            else -> throw IllegalArgumentException("Unsupported HTTP method: $method")
        }

    private fun configureRequest(
        requestBuilder: MockHttpServletRequestBuilder,
        requestBody: Any?,
        parameters: Map<String, String>,
    ): MockHttpServletRequestBuilder =
        requestBuilder.apply {
            contentType(MediaType.APPLICATION_JSON)
            accept(MediaType.APPLICATION_JSON)
            requestBody?.let { content(objectMapper.writeValueAsString(it)) }
            if (parameters.isNotEmpty()) {
                params(toMultiValueMap(parameters))
            }
        }

    private fun toMultiValueMap(map: Map<String, String>): MultiValueMap<String, String> =
        LinkedMultiValueMap<String, String>().apply {
            map.forEach { (key, value) -> add(key, value) }
        }
}
