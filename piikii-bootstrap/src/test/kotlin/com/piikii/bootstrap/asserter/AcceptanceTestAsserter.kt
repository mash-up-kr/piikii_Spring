package com.piikii.bootstrap.asserter

import com.piikii.input.http.controller.dto.ResponseForm
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultMatcher

interface AcceptanceTestAsserter {
    fun <T> execute(
        result: ResultActions,
        expectedStatus: ResultMatcher,
        responseDto: ResponseForm<T>,
    )

    fun execute(
        result: ResultActions,
        expectedStatus: ResultMatcher,
    )
}
