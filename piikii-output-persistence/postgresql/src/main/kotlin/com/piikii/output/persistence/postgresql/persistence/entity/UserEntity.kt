package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.application.domain.user.User
import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "piikii_user", schema = "piikii")
@SQLRestriction("is_deleted = false")
@DynamicUpdate
class UserEntity(
    @Column(name = "room_id", nullable = false)
    val roomId: Long,
    @Column(name = "voted")
    val voted: Boolean = false,
) : BaseEntity() {
    fun toDomain(): User {
        return User(
            roomId = this.roomId,
            voted = this.voted,
        )
    }

    companion object {
        fun from(user: User): UserEntity {
            return UserEntity(
                roomId = user.roomId,
                voted = user.voted,
            )
        }
    }
}
