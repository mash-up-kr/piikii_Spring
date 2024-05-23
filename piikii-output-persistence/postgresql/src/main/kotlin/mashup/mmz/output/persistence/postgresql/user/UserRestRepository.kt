package mashup.mmz.output.persistence.postgresql.user

import mashup.mmz.application.domain.model.User
import mashup.mmz.application.port.output.persistence.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRestRepository(
    private val userJpaRepository: UserJpaRepository
) : UserRepository<User, Long> {

    override fun save(user: User): User {
        userJpaRepository.save(UserEntity.toEntity(user))
        return User("fasd")
    }

    override fun retrieve(pk: Long): User {
        TODO("Not yet implemented")
    }

    override fun retrieveAll(pkList: List<Long>): List<User> {
        TODO("Not yet implemented")
    }

    override fun update(e: User, pk: Long): Void {
        TODO("Not yet implemented")
    }

    override fun delete(pk: Long): Void {
        TODO("Not yet implemented")
    }
}