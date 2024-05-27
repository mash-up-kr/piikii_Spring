package mashup.mmz.output.persistence.postgresql.user

import jakarta.persistence.Entity
import jakarta.persistence.Table
import mashup.mmz.application.domain.model.User
import mashup.mmz.output.persistence.postgresql.BaseEntity
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy

@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class UserEntity(
    private val loginId: String
) : BaseEntity() {

    companion object {
        fun toEntity(user: User): UserEntity {
            return UserEntity(user.loginId)
        }
    }
}