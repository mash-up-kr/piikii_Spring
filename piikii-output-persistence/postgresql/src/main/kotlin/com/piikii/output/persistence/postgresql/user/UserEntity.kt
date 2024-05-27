package com.piikii.output.persistence.postgresql.user

import com.piikii.application.domain.model.User
import com.piikii.output.persistence.postgresql.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.Cache
import org.hibernate.annotations.CacheConcurrencyStrategy

@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
class UserEntity(
    @Column(name = "loginId", nullable = false)
    private val loginId: String
) : BaseEntity() {

    companion object {
        fun toEntity(user: User): UserEntity {
            return UserEntity(user.loginId)
        }
    }
}
