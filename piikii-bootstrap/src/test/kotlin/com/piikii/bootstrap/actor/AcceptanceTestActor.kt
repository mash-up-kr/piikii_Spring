package com.piikii.bootstrap.actor

import org.springframework.http.HttpMethod
import org.springframework.test.web.servlet.ResultActions

interface AcceptanceTestActor {
    fun execute(
        method: HttpMethod,
        uri: String,
        requestBody: Any?,
        parameters: Map<String, String>,
    ): ResultActions
}
