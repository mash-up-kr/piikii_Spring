package com.piikii.input.http

import com.piikii.common.error.ErrorCode
import com.piikii.common.error.PiikiiError
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/test-throw/illegalArgument")
    fun testThrow400() {
        val userId = 4L
        throw PiikiiError(
            errorCode = ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION,
            detailMessage = "userId : ${userId}"
        )
    }

    @GetMapping("/test-throw/unauth")
    fun testThrow401() {
        throw PiikiiError(ErrorCode.UNAUTHORIZED)
    }

    @GetMapping("/test-throw/accessDenied")
    fun testThrow403() {
        val user = "보호된 리소스 대상인 유저"
        throw PiikiiError(ErrorCode.ACCESS_DENIED)
    }

    @GetMapping("/test-throw/notFounded")
    fun testThrow404() {
        val userId = 1L
        throw PiikiiError(ErrorCode.NOT_FOUNDED)
    }

    @GetMapping("/test-throw/conflict")
    fun testThrow409() {
        val userId = 1L
        throw PiikiiError(ErrorCode.CONFLICT)
    }
}
