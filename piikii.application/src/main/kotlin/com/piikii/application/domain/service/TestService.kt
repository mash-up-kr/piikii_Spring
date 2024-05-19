package com.piikii.application.domain.service

import com.piikii.application.port.input.TestInPort
import com.piikii.application.port.output.TestOutPort
import org.springframework.stereotype.Service

@Service
class TestService(
    private val testOutPort: TestOutPort
) : TestInPort {
    override fun test() {
        println(testOutPort.output())
    }
}
