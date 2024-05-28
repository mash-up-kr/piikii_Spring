package com.piikii.input.http

import com.piikii.application.port.input.UserUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserApi(
    private val userUseCase: UserUseCase
) {

    @GetMapping("/test")
    fun test() {
        println("dsfklfjdsalkfjsadlkfjdsalkf")
        userUseCase.save("sdf")
    }
}
