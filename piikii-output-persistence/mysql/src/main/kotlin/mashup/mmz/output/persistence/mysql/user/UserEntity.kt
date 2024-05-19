package mashup.mmz.output.persistence.mysql.user

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import mashup.mmz.application.domain.model.User
import mashup.mmz.output.persistence.mysql.BaseEntity
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class UserEntity(
    private val loginId: String
) : BaseEntity() {


    companion object {
        fun toEntity(user: User): UserEntity {
            return UserEntity(user.loginId)
        }
    }
}