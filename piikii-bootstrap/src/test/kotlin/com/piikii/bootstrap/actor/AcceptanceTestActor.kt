package com.piikii.bootstrap.actor

import org.springframework.http.HttpMethod
import org.springframework.test.web.servlet.ResultActions

interface AcceptanceTestActor {
    fun <T> execute(
        method: HttpMethod,
        uri: String,
        requestBody: T?,
        parameters: Map<String, String>,
    ): ResultActions
}
