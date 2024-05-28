package com.piikii.output.persistence.postgresql.user

import com.piikii.application.domain.model.User
import com.piikii.application.port.output.persistence.UserCommandPort
import com.piikii.application.port.output.persistence.UserQueryPort
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val userJpaRepository: UserJpaRepository
) : UserQueryPort, UserCommandPort {

    override fun save(user: User): User {
        userJpaRepository.save(UserEntity.toEntity(user))
        return User("fasd")
    }

    override fun retrieve(id: Long): User {
        TODO("Not yet implemented")
    }

    override fun retrieveAll(ids: List<Long>): List<User> {
        TODO("Not yet implemented")
    }

    override fun update(user: User, id: Long) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }
}
