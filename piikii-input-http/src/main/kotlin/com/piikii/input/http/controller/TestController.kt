package com.piikii.input.http.controller

import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.input.http.dto.ResponseForm
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @GetMapping("/test-throw/illegalArgument")
    fun testThrow400() {
        val userId = 4L
        throw PiikiiException(
            exceptionCode = ExceptionCode.ILLEGAL_ARGUMENT_EXCEPTION,
            detailMessage = "userId : $userId",
        )
    }

    @GetMapping("/test-throw/unauth")
    fun testThrow401() {
        throw PiikiiException(ExceptionCode.UNAUTHORIZED)
    }

    @GetMapping("/test-throw/accessDenied")
    fun testThrow403() {
        val user = "보호된 리소스 대상인 유저"
        throw PiikiiException(ExceptionCode.ACCESS_DENIED)
    }

    @GetMapping("/test-throw/notFounded")
    fun testThrow404() {
        val userId = 1L
        throw PiikiiException(ExceptionCode.NOT_FOUNDED)
    }

    @GetMapping("/test-throw/conflict")
    fun testThrow409() {
        val userId = 1L
        throw PiikiiException(ExceptionCode.CONFLICT)
    }

    @GetMapping("/api")
    fun apiResponse(): ResponseForm<String> {
        return ResponseForm("결과")
    }

    @GetMapping("/api/message")
    fun apiResponseMessage(): ResponseForm<String> {
        return ResponseForm("결과", "부가적인 메세지~")
    }
}
