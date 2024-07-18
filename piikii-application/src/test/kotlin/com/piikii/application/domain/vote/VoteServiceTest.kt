package com.piikii.application.domain.vote

import com.piikii.application.domain.generic.Origin
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.room.Password
import com.piikii.application.domain.room.Room
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.application.port.output.persistence.VoteCommandPort
import com.piikii.common.exception.PiikiiException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.UUID
import java.util.stream.Stream

@ExtendWith(MockitoExtension::class)
class VoteServiceTest {
    @InjectMocks
    lateinit var voteService: VoteService

    @Mock
    lateinit var voteCommandPort: VoteCommandPort

    @Mock
    lateinit var roomQueryPort: RoomQueryPort

    @Mock
    lateinit var placeQueryPort: PlaceQueryPort

    @MethodSource("voteUnavailableRoom")
    @ParameterizedTest
    fun `Room Vote가 불가능한 상황에서 vote 요청 시 Exception이 발생한다`(room: Room) {
        // given, when
        given(roomQueryPort.findById(room.roomUid))
            .willReturn(room)

        // then
        assertThatThrownBy { voteService.vote(room.roomUid, emptyList()) }
            .isExactlyInstanceOf(PiikiiException::class.java)
            .hasMessageContaining("투표가 시작되지 않았거나, 마감되었습니다")
    }

    @Test
    fun `Vote Place Id가 존재하지 않으면 Exception이 발생한다`() {
        // given
        val userId = UUID.randomUUID()
        val roomUid = UUID.randomUUID()

        val room =
            Room(
                name = "BB Kim",
                message = null,
                thumbnailLink = "https://test",
                password = Password("1234"),
                voteDeadline = LocalDateTime.now().plusDays(1),
                roomUid = roomUid,
            )
        val votes =
            listOf(
                Vote(userId = userId, placeId = 1, result = VoteResult.GOOD),
                Vote(userId = userId, placeId = 2, result = VoteResult.BAD),
                Vote(userId = userId, placeId = 3, result = VoteResult.GOOD),
            )
        val place =
            Place(
                id = 0L,
                url = null,
                thumbnailLinks = ThumbnailLinks(contents = null),
                address = null,
                phoneNumber = null,
                starGrade = null,
                origin = Origin.MANUAL,
                roomUid = roomUid,
                scheduleId = 0L,
                memo = null,
            )

        given(roomQueryPort.findById(room.roomUid))
            .willReturn(room)
        given(placeQueryPort.findAllByPlaceIds(votes.map { it.placeId }))
            .willReturn(listOf(place))

        // when & then
        assertThatThrownBy { voteService.vote(room.roomUid, votes) }
            .isExactlyInstanceOf(PiikiiException::class.java)
            .hasMessageContaining("투표 항목 데이터(Place Id)이 올바르지 않습니다")
    }

    @Test
    fun `Vote Place Id가 해당 Room에 속하지 않을 경우, Exception이 발생한다`() {
        // given
        val userId = UUID.randomUUID()
        val roomUid = UUID.randomUUID()

        val room =
            Room(
                name = "BB Kim",
                message = null,
                thumbnailLink = "https://test",
                password = Password("1234"),
                voteDeadline = LocalDateTime.now().plusDays(1),
                roomUid = roomUid,
            )
        val votes =
            listOf(
                Vote(userId = userId, placeId = 1, result = VoteResult.GOOD),
                Vote(userId = userId, placeId = 2, result = VoteResult.BAD),
            )
        val places =
            listOf(
                Place(
                    id = 0L,
                    url = null,
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = UUID.randomUUID(),
                    scheduleId = 0L,
                    memo = null,
                ),
                Place(
                    id = 1L,
                    url = null,
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = UUID.randomUUID(),
                    scheduleId = 0L,
                    memo = null,
                ),
            )

        given(roomQueryPort.findById(room.roomUid))
            .willReturn(room)
        given(placeQueryPort.findAllByPlaceIds(votes.map { it.placeId }))
            .willReturn(places)

        // when & then
        assertThatThrownBy { voteService.vote(room.roomUid, votes) }
            .isExactlyInstanceOf(PiikiiException::class.java)
            .hasMessageContaining("투표 항목 데이터(Place Id)이 올바르지 않습니다")
    }

    @Test
    fun `올바른 데이터에서 vote는 성공한다`() {
        // given
        val userId = UUID.randomUUID()
        val roomUid = UUID.randomUUID()

        val room =
            Room(
                name = "BB Kim",
                message = null,
                thumbnailLink = "https://test",
                password = Password("1234"),
                voteDeadline = LocalDateTime.now().plusDays(1),
                roomUid = roomUid,
            )
        val votes =
            listOf(
                Vote(userId = userId, placeId = 1, result = VoteResult.GOOD),
                Vote(userId = userId, placeId = 2, result = VoteResult.BAD),
            )
        val places =
            listOf(
                Place(
                    id = 1,
                    url = null,
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = roomUid,
                    scheduleId = 0,
                    memo = null,
                ),
                Place(
                    id = 2,
                    url = null,
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = roomUid,
                    scheduleId = 0,
                    memo = null,
                ),
            )

        given(roomQueryPort.findById(roomUid))
            .willReturn(room)
        given(placeQueryPort.findAllByPlaceIds(votes.map { it.placeId }))
            .willReturn(places)

        // when & then
        assertDoesNotThrow { voteService.vote(roomUid, votes) }
        verify(voteCommandPort).save(votes)
    }

    companion object {
        @JvmStatic
        fun voteUnavailableRoom(): Stream<Room> {
            val roomUid = UUID.randomUUID()
            return Stream.of(
                Room(
                    name = "BB Kim",
                    message = null,
                    thumbnailLink = "https://test",
                    password = Password("1234"),
                    voteDeadline = null,
                    roomUid = roomUid,
                ),
                Room(
                    name = "BB Kim",
                    message = null,
                    thumbnailLink = "https://test",
                    password = Password("1234"),
                    voteDeadline = LocalDateTime.of(1995, 9, 25, 1, 1, 1),
                    roomUid = roomUid,
                ),
            )
        }
    }
}
