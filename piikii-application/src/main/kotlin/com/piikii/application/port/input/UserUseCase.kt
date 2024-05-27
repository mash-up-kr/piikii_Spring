package com.piikii.application.port.input

import com.piikii.application.domain.model.User

interface UserUseCase {
    fun save(loginId: String): User
}
