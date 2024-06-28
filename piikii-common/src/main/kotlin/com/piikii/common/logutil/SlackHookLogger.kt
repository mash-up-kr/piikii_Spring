package com.piikii.common.logutil

import com.piikii.common.logutil.client.HookLoggerConfig
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class SlackHookLogger(
    private val hookLoggerConfig: HookLoggerConfig,
) {
    @Async("hookLoggerTaskExecutor")
    fun send(message: String) {
        hookLoggerConfig.getSlackHookLoggerInstance()
            .post()
            .body(mapOf("text" to message))
            .retrieve()
            .toBodilessEntity()
    }
}
