package com.piikii.adapter.input.http

import com.piikii.application.port.input.TestInPort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val testInPort: TestInPort
) {

    @GetMapping("/test")
    fun test() {
        testInPort.test()
    }
}
