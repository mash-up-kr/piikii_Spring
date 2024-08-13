package com.piikii.application.domain.fixture

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.room.Password
import com.piikii.application.domain.room.Room
import java.time.LocalDateTime
import java.util.UUID

class RoomFixture(
    private var roomUid: UuidTypeId = UuidTypeId(UUID.randomUUID()),
    private var name: String = "강남에서 모이자",
    private var message: String? = null,
    private var thumbnailLink: String = "https://test",
    private var password: Password = Password("1234"),
    private var voteDeadline: LocalDateTime? = null,
) {
    fun roomUid(roomUid: UUID): RoomFixture {
        this.roomUid = UuidTypeId(roomUid)
        return this
    }

    fun voteDeadline(deadline: LocalDateTime): RoomFixture {
        this.voteDeadline = deadline
        return this
    }

    fun build(): Room {
        return Room(
            roomUid = this.roomUid,
            name = this.name,
            message = this.message,
            thumbnailLink = this.thumbnailLink,
            password = this.password,
            voteDeadline = this.voteDeadline,
        )
    }

    companion object {
        fun create() = RoomFixture()
    }
}
