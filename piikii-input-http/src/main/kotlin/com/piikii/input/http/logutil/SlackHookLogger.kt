package com.piikii.input.http.logutil

import com.piikii.common.client.PiikiiRestClient
import com.piikii.common.logutil.HookLogger
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class SlackHookLogger(
    private val piikiiRestClient: PiikiiRestClient,
) : HookLogger {
    @Async("hookLoggerTaskExecutor")
    override fun send(message: String) {
        piikiiRestClient.build()
            .post()
            .body(mapOf("text" to message))
            .retrieve()
            .toBodilessEntity()
    }
}
