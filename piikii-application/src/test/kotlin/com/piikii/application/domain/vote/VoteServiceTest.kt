package com.piikii.application.domain.vote

import com.piikii.application.domain.generic.Source
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.room.Password
import com.piikii.application.domain.room.Room
import com.piikii.application.domain.schedule.PlaceType
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
import java.time.LocalDate
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
        given(roomQueryPort.findById(room.roomId))
            .willReturn(room)

        // then
        assertThatThrownBy { voteService.vote(room.roomId, emptyList()) }
            .isExactlyInstanceOf(PiikiiException::class.java)
            .hasMessageContaining("투표가 시작되지 않았거나, 마감되었습니다")
    }

    @Test
    fun `Vote Place Id가 존재하지 않으면 Exception이 발생한다`() {
        // given
        val userId = UUID.randomUUID()
        val roomId = UUID.randomUUID()

        val room =
            Room(
                meetingName = "BB Kim",
                message = null,
                address = "gunpo si",
                meetDay = LocalDate.now(),
                thumbnailLink = "https://test",
                password = Password("1234"),
                voteDeadline = LocalDateTime.now().plusDays(1),
                roomId = roomId,
            )
        val votes =
            listOf(
                Vote(userId = userId, placeId = 1, result = VoteResult.O),
                Vote(userId = userId, placeId = 2, result = VoteResult.X),
                Vote(userId = userId, placeId = 3, result = VoteResult.O),
            )
        val place =
            Place(
                id = null,
                placeType = PlaceType.ARCADE,
                url = null,
                thumbnailLinks = ThumbnailLinks(contents = null),
                address = null,
                phoneNumber = null,
                starGrade = null,
                source = Source.MANUAL,
                roomId = roomId,
            )

        // when
        given(roomQueryPort.findById(room.roomId))
            .willReturn(room)
        given(placeQueryPort.findAllByPlaceIds(votes.map { it.placeId }))
            .willReturn(listOf(place))

        // then
        assertThatThrownBy { voteService.vote(room.roomId, votes) }
            .isExactlyInstanceOf(PiikiiException::class.java)
            .hasMessageContaining("투표 항목 데이터(Place Id)이 올바르지 않습니다")
    }

    @Test
    fun `Vote Place Id가 해당 Room에 속하지 않을 경우, Exception이 발생한다`() {
        // given
        val userId = UUID.randomUUID()
        val roomId = UUID.randomUUID()

        val room =
            Room(
                meetingName = "BB Kim",
                message = null,
                address = "gunpo si",
                meetDay = LocalDate.now(),
                thumbnailLink = "https://test",
                password = Password("1234"),
                voteDeadline = LocalDateTime.now().plusDays(1),
                roomId = roomId,
            )
        val votes =
            listOf(
                Vote(userId = userId, placeId = 1, result = VoteResult.O),
                Vote(userId = userId, placeId = 2, result = VoteResult.X),
            )
        val places =
            listOf(
                Place(
                    id = null,
                    placeType = PlaceType.ARCADE,
                    url = null,
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    source = Source.MANUAL,
                    roomId = UUID.randomUUID(),
                ),
                Place(
                    id = null,
                    placeType = PlaceType.ARCADE,
                    url = null,
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    source = Source.MANUAL,
                    roomId = UUID.randomUUID(),
                ),
            )

        // when
        given(roomQueryPort.findById(room.roomId))
            .willReturn(room)
        given(placeQueryPort.findAllByPlaceIds(votes.map { it.placeId }))
            .willReturn(places)

        // then
        assertThatThrownBy { voteService.vote(room.roomId, votes) }
            .isExactlyInstanceOf(PiikiiException::class.java)
            .hasMessageContaining("투표 항목 데이터(Place Id)이 올바르지 않습니다")
    }

    @Test
    fun `올바른 데이터에서 vote는 성공한다`() {
        // given
        val userId = UUID.randomUUID()
        val roomId = UUID.randomUUID()

        val room =
            Room(
                meetingName = "BB Kim",
                message = null,
                address = "gunpo si",
                meetDay = LocalDate.now(),
                thumbnailLink = "https://test",
                password = Password("1234"),
                voteDeadline = LocalDateTime.now().plusDays(1),
                roomId = roomId,
            )
        val votes =
            listOf(
                Vote(userId = userId, placeId = 1, result = VoteResult.O),
                Vote(userId = userId, placeId = 2, result = VoteResult.X),
            )
        val places =
            listOf(
                Place(
                    id = null,
                    placeType = PlaceType.ARCADE,
                    url = null,
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    source = Source.MANUAL,
                    roomId = roomId,
                ),
                Place(
                    id = null,
                    placeType = PlaceType.ARCADE,
                    url = null,
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    source = Source.MANUAL,
                    roomId = roomId,
                ),
            )

        // when
        given(roomQueryPort.findById(roomId))
            .willReturn(room)
        given(placeQueryPort.findAllByPlaceIds(votes.map { it.placeId }))
            .willReturn(places)

        // then
        assertDoesNotThrow { voteService.vote(roomId, votes) }
        verify(voteCommandPort).vote(votes)
    }

    companion object {
        @JvmStatic
        fun voteUnavailableRoom(): Stream<Room> {
            val roomId = UUID.randomUUID()
            return Stream.of(
                Room(
                    meetingName = "BB Kim",
                    message = null,
                    address = "gunpo si",
                    meetDay = LocalDate.now(),
                    thumbnailLink = "https://test",
                    password = Password("1234"),
                    voteDeadline = null,
                    roomId = roomId,
                ),
                Room(
                    meetingName = "BB Kim",
                    message = null,
                    address = "gunpo si",
                    meetDay = LocalDate.now(),
                    thumbnailLink = "https://test",
                    password = Password("1234"),
                    voteDeadline = LocalDateTime.of(1995, 9, 25, 1, 1, 1),
                    roomId = roomId,
                ),
            )
        }
    }
}
