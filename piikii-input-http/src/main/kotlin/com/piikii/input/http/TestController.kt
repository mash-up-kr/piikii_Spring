package com.piikii.input.http

import com.piikii.common.exception.PiikiiException.Companion.accessDeniedException
import com.piikii.common.exception.PiikiiException.Companion.conflictException
import com.piikii.common.exception.PiikiiException.Companion.illegalArgumentException
import com.piikii.common.exception.PiikiiException.Companion.notFoundedException
import com.piikii.common.exception.PiikiiException.Companion.unauthorizedException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/test-throw/illegalArgument")
    fun testThrow400() {
        val userId = 4L
        illegalArgumentException(
            fieldName = "userId",
            fieldValue = userId
        )
    }

    @GetMapping("/test-throw/unauth")
    fun testThrow401() {
        unauthorizedException()
    }

    @GetMapping("/test-throw/accessDenied")
    fun testThrow403() {
        val user = "보호된 리소스 대상인 유저"
        accessDeniedException(
            fieldName = "user"
        )
    }

    @GetMapping("/test-throw/notFounded")
    fun testThrow404() {
        val userId = 1L
        notFoundedException(
            fieldName = "userId",
            fieldValue = userId
        )
    }

    @GetMapping("/test-throw/conflict")
    fun testThrow409() {
        val userId = 1L
        conflictException(
            fieldName = "userId",
            fieldValue = userId
        )
    }
}
