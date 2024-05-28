package com.piikii.application.port.output.persistence

import com.piikii.application.domain.model.User

interface UserQueryPort {
    fun retrieve(id: Long): User
    fun retrieveAll(ids: List<Long>): List<User>
}

interface UserCommandPort {
    fun save(user: User): User
    fun update(user: User, id: Long)
    fun delete(id: Long)
}
