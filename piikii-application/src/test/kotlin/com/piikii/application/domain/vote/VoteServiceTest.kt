package com.piikii.application.domain.vote

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.room.Password
import com.piikii.application.domain.room.Room
import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.domain.schedule.ScheduleType
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import com.piikii.application.port.output.persistence.VoteCommandPort
import com.piikii.application.port.output.persistence.VoteQueryPort
import com.piikii.common.exception.PiikiiException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.BDDMockito.anyList
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
    lateinit var voteQueryPort: VoteQueryPort

    @Mock
    lateinit var voteCommandPort: VoteCommandPort

    @Mock
    lateinit var roomQueryPort: RoomQueryPort

    @Mock
    lateinit var placeQueryPort: PlaceQueryPort

    @Mock
    lateinit var scheduleQueryPort: ScheduleQueryPort

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
        val userUid = UuidTypeId(UUID.randomUUID())
        val roomUid = UuidTypeId(UUID.randomUUID())

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
                Vote(
                    id = LongTypeId(null),
                    userUid = userUid,
                    placeId = LongTypeId(1),
                    result = VoteResult.AGREE,
                ),
                Vote(
                    id = LongTypeId(null),
                    userUid = userUid,
                    placeId = LongTypeId(2),
                    result = VoteResult.DISAGREE,
                ),
                Vote(
                    id = LongTypeId(null),
                    userUid = userUid,
                    placeId = LongTypeId(3),
                    result = VoteResult.AGREE,
                ),
            )
        val place =
            Place(
                id = LongTypeId(0L),
                name = "",
                url = null,
                thumbnailLinks = ThumbnailLinks(contents = null),
                address = null,
                phoneNumber = null,
                starGrade = null,
                origin = Origin.MANUAL,
                roomUid = roomUid,
                scheduleId = LongTypeId(0L),
                memo = null,
                confirmed = false,
                longitude = 126.9246033,
                latitude = 33.45241976,
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
        val userUid = UuidTypeId(UUID.randomUUID())
        val roomUid = UuidTypeId(UUID.randomUUID())

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
                Vote(id = LongTypeId(null), userUid = userUid, placeId = LongTypeId(1), result = VoteResult.AGREE),
                Vote(id = LongTypeId(null), userUid = userUid, placeId = LongTypeId(2), result = VoteResult.DISAGREE),
            )
        val places =
            listOf(
                Place(
                    id = LongTypeId(0L),
                    url = null,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = UuidTypeId(UUID.randomUUID()),
                    scheduleId = LongTypeId(0L),
                    memo = null,
                    confirmed = false,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
                ),
                Place(
                    id = LongTypeId(1L),
                    url = null,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = UuidTypeId(UUID.randomUUID()),
                    scheduleId = LongTypeId(0L),
                    memo = null,
                    confirmed = false,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
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
        val userUid = UuidTypeId(UUID.randomUUID())
        val roomUid = UuidTypeId(UUID.randomUUID())

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
                Vote(id = LongTypeId(null), userUid = userUid, placeId = LongTypeId(1), result = VoteResult.AGREE),
                Vote(id = LongTypeId(null), userUid = userUid, placeId = LongTypeId(2), result = VoteResult.DISAGREE),
            )
        val places =
            listOf(
                Place(
                    id = LongTypeId(1),
                    url = null,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = roomUid,
                    scheduleId = LongTypeId(0),
                    memo = null,
                    confirmed = false,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
                ),
                Place(
                    id = LongTypeId(2),
                    url = null,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = roomUid,
                    scheduleId = LongTypeId(0),
                    memo = null,
                    confirmed = false,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
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

    @Test
    fun `투표 결과 정상조회 테스트`() {
        // given
        val userUid = UuidTypeId(UUID.randomUUID())
        val roomUid = UuidTypeId(UUID.randomUUID())

        val votes =
            listOf(
                Vote(id = LongTypeId(null), userUid = userUid, placeId = LongTypeId(1), result = VoteResult.AGREE),
                Vote(id = LongTypeId(null), userUid = userUid, placeId = LongTypeId(3), result = VoteResult.AGREE),
                Vote(id = LongTypeId(null), userUid = userUid, placeId = LongTypeId(3), result = VoteResult.AGREE),
                Vote(id = LongTypeId(null), userUid = userUid, placeId = LongTypeId(2), result = VoteResult.DISAGREE),
            )
        val schedules =
            listOf(
                Schedule(id = LongTypeId(1), roomUid = roomUid, name = "식사", sequence = 1, type = ScheduleType.DISH),
                Schedule(id = LongTypeId(2), roomUid = roomUid, name = "술", sequence = 2, type = ScheduleType.ALCOHOL),
                Schedule(id = LongTypeId(3), roomUid = roomUid, name = "카페", sequence = 3, type = ScheduleType.DESSERT),
            )
        val places =
            listOf(
                Place(
                    id = LongTypeId(1),
                    url = null,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = roomUid,
                    scheduleId = LongTypeId(1),
                    memo = null,
                    confirmed = false,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
                ),
                Place(
                    id = LongTypeId(2),
                    url = null,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = roomUid,
                    scheduleId = LongTypeId(2),
                    memo = null,
                    confirmed = false,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
                ),
                Place(
                    id = LongTypeId(3),
                    url = null,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = roomUid,
                    scheduleId = LongTypeId(2),
                    memo = null,
                    confirmed = false,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
                ),
            )

        given(placeQueryPort.findAllByRoomUid(roomUid))
            .willReturn(places)
        given(scheduleQueryPort.findAllByRoomUid(roomUid))
            .willReturn(schedules)
        given(voteQueryPort.findAllByPlaceIds(anyList()))
            .willReturn(votes)
        given(voteQueryPort.findAgreeCountByPlaceId(votes))
            .willReturn(
                votes
                    .filter { it.result == VoteResult.AGREE }
                    .groupingBy { it.placeId.getValue() }
                    .eachCount(),
            )

        // when
        val voteResultResponse = assertDoesNotThrow { voteService.getVoteResultOfRoom(roomUid) }

        // then
        assertThat(voteResultResponse.result).hasSize(schedules.size)

        val scheduleTwoResponse = voteResultResponse.result.find { it.scheduleId == 2L }!!
        assertThat(scheduleTwoResponse.places).hasSize(2)

        // 동의 투표 수 기준 내림차순 정렬 여부 확인
        assertThat(scheduleTwoResponse.places[0].countOfAgree).isEqualTo(2)
    }

    companion object {
        @JvmStatic
        fun voteUnavailableRoom(): Stream<Room> {
            val roomUid = UuidTypeId(UUID.randomUUID())
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
