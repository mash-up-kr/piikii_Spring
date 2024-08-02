package com.piikii.bootstrap

import com.piikii.bootstrap.actor.AcceptanceTestActor
import com.piikii.bootstrap.annotation.AcceptanceTest
import com.piikii.bootstrap.asserter.AcceptanceTestAsserter
import com.piikii.input.http.controller.dto.ResponseForm
import org.springframework.http.HttpMethod
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultMatcher

@AcceptanceTest
abstract class AcceptanceTestHelper(
    private val acceptanceTestActor: AcceptanceTestActor,
    private val acceptanceTestAsserter: AcceptanceTestAsserter,
) {
    fun <T> act(
        method: HttpMethod,
        uri: String,
        requestBody: T?,
        parameters: Map<String, String> = emptyMap(),
    ): ResultActions {
        return acceptanceTestActor.execute(
            method = method,
            uri = uri,
            requestBody = requestBody,
            parameters = parameters,
        )
    }

    fun <T> assert(
        resultActions: ResultActions,
        expectedStatus: ResultMatcher,
        responseDto: ResponseForm<T>,
    ) {
        acceptanceTestAsserter.execute(resultActions, expectedStatus, responseDto)
    }

    fun assert(
        resultActions: ResultActions,
        expectedStatus: ResultMatcher,
    ) {
        acceptanceTestAsserter.execute(resultActions, expectedStatus)
    }
}
