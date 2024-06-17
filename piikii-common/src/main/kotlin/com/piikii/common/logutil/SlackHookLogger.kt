package com.piikii.common.logutil

import org.springframework.stereotype.Component

@Component
class SlackHookLogger : HookLogger {
    override fun send(message: String) {
        TODO("Not yet implemented")
    }
}
