package com.piikii.output.persistence.postgresql.persistence.entity

import com.piikii.output.persistence.postgresql.persistence.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "room_user", schema = "piikii")
class RoomUserEntity(
    @Column(name = "room_id", nullable = false)
    val roomId: Long,

    @Column(name = "voted")
    val voted: Boolean? = null
) : BaseEntity()
