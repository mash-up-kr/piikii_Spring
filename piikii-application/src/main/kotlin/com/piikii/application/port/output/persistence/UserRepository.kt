package com.piikii.application.port.output.persistence

import com.piikii.application.domain.model.User

interface UserRepository<E : User, P : Long> {
    fun save(e: E): E
    fun retrieve(pk: P): E
    fun retrieveAll(pkList: List<P>): List<E>
    fun update(e: E, pk: P): Void
    fun delete(pk: P): Void
}
