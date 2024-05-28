package com.piikii.application.port.input

import com.piikii.application.domain.model.User

interface UserUsecase {
    fun save(loginId: String): User
}
