package com.piikii.application.domain.course

import com.piikii.application.domain.generic.LongTypeId
import com.piikii.application.domain.generic.ThumbnailLinks
import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.domain.place.Origin
import com.piikii.application.domain.place.Place
import com.piikii.application.domain.room.Password
import com.piikii.application.domain.room.Room
import com.piikii.application.domain.schedule.Schedule
import com.piikii.application.domain.schedule.ScheduleType
import com.piikii.application.domain.vote.Vote
import com.piikii.application.domain.vote.VoteResult
import com.piikii.application.port.output.persistence.CourseQueryPort
import com.piikii.application.port.output.persistence.PlaceCommandPort
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import com.piikii.application.port.output.persistence.VoteQueryPort
import com.piikii.application.port.output.web.NavigationClient
import com.piikii.common.exception.PiikiiException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.anyList
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class CourseServiceTest {
    @InjectMocks
    lateinit var courseService: CourseService

    @Mock
    lateinit var roomQueryPort: RoomQueryPort

    @Mock
    lateinit var scheduleQueryPort: ScheduleQueryPort

    @Mock
    lateinit var placeQueryPort: PlaceQueryPort

    @Mock
    lateinit var placeCommandPort: PlaceCommandPort

    @Mock
    lateinit var voteQueryPort: VoteQueryPort

    @Mock
    lateinit var navigationClient: NavigationClient

    @Mock
    lateinit var courseQueryPort: CourseQueryPort

    @Test
    fun `VoteDeadline 이 현재보다 이후이면 Exception 이 발생한다`() {
        // given
        val room =
            Room(
                roomUid = UuidTypeId(UUID.randomUUID()),
                name = "사당에서 모이자",
                message = null,
                thumbnailLink = "https://test",
                password = Password("1234"),
                voteDeadline = LocalDateTime.now().plusDays(1),
            )

        given(roomQueryPort.findById(room.roomUid)).willReturn(room)

        // when & then
        assertThatThrownBy { courseService.retrieveCourse(room.roomUid) }
            .isExactlyInstanceOf(PiikiiException::class.java)
            .hasMessageContaining(CourseService.VOTE_NOT_END)
    }

    @Test
    fun `투표 시작 전이면 Exception 이 발생한다`() {
        // given
        val room =
            Room(
                roomUid = UuidTypeId(UUID.randomUUID()),
                name = "사당에서 모이자",
                message = null,
                thumbnailLink = "https://test",
                password = Password("1234"),
                voteDeadline = null,
            )

        given(roomQueryPort.findById(room.roomUid)).willReturn(room)

        // when & then
        assertThatThrownBy { courseService.retrieveCourse(room.roomUid) }
            .isExactlyInstanceOf(PiikiiException::class.java)
            .hasMessageContaining(CourseService.VOTE_NOT_END)
    }

    @Test
    fun `Room 의 각 Schedule 중 confirmed 된 Place 목록을 조회한다`() {
        // given
        val room =
            Room(
                roomUid = UuidTypeId(UUID.randomUUID()),
                name = "사당에서 모이자",
                message = null,
                thumbnailLink = "https://test",
                password = Password("1234"),
                voteDeadline = LocalDateTime.now().minusDays(1),
            )

        val schedules =
            listOf(
                Schedule(
                    id = LongTypeId(1),
                    roomUid = room.roomUid,
                    name = "1차",
                    sequence = 1,
                    type = ScheduleType.DISH,
                ),
                Schedule(
                    id = LongTypeId(2),
                    roomUid = room.roomUid,
                    name = "2차",
                    sequence = 2,
                    type = ScheduleType.ALCOHOL,
                ),
                Schedule(
                    id = LongTypeId(3),
                    roomUid = room.roomUid,
                    name = "3차",
                    sequence = 3,
                    type = ScheduleType.DESSERT,
                ),
                Schedule(
                    id = LongTypeId(4),
                    roomUid = room.roomUid,
                    name = "4차",
                    sequence = 4,
                    type = ScheduleType.ARCADE,
                ),
            )

        val placeUrl1 = "주소를 입력하세요1"
        val placeUrl2 = "주소를 입력하세요2"
        val placeUrl3 = "주소를 입력하세요3"
        val placeUrl4 = "주소를 입력하세요4"
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
                    roomUid = room.roomUid,
                    scheduleId = schedules[0].id,
                    memo = null,
                    confirmed = false,
                    reviewCount = 0,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
                ),
                Place(
                    id = LongTypeId(2),
                    url = placeUrl1,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.AVOCADO,
                    roomUid = room.roomUid,
                    scheduleId = schedules[0].id,
                    memo = null,
                    confirmed = true,
                    reviewCount = 0,
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
                    roomUid = room.roomUid,
                    scheduleId = schedules[1].id,
                    memo = null,
                    confirmed = false,
                    reviewCount = 0,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
                ),
                Place(
                    id = LongTypeId(4),
                    url = null,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = room.roomUid,
                    scheduleId = schedules[1].id,
                    memo = null,
                    confirmed = false,
                    reviewCount = 0,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
                ),
                Place(
                    id = LongTypeId(5),
                    url = placeUrl2,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.AVOCADO,
                    roomUid = room.roomUid,
                    scheduleId = schedules[1].id,
                    memo = null,
                    confirmed = true,
                    reviewCount = 0,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
                ),
                Place(
                    id = LongTypeId(6),
                    url = null,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = room.roomUid,
                    scheduleId = schedules[2].id,
                    memo = null,
                    confirmed = false,
                    reviewCount = 0,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
                ),
                Place(
                    id = LongTypeId(7),
                    url = placeUrl3,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.AVOCADO,
                    roomUid = room.roomUid,
                    scheduleId = schedules[2].id,
                    memo = null,
                    confirmed = true,
                    reviewCount = 0,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
                ),
                Place(
                    id = LongTypeId(8),
                    url = null,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = room.roomUid,
                    scheduleId = schedules[3].id,
                    memo = null,
                    confirmed = false,
                    reviewCount = 0,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
                ),
                Place(
                    id = LongTypeId(9),
                    url = null,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.MANUAL,
                    roomUid = room.roomUid,
                    scheduleId = schedules[3].id,
                    memo = null,
                    confirmed = false,
                    reviewCount = 0,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
                ),
                Place(
                    id = LongTypeId(10),
                    url = placeUrl4,
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.AVOCADO,
                    roomUid = room.roomUid,
                    scheduleId = schedules[3].id,
                    memo = null,
                    confirmed = false,
                    reviewCount = 0,
                    longitude = 126.9246033,
                    latitude = 33.45241976,
                ),
            )

        val userUid = UuidTypeId(UUID.randomUUID())
        val votes =
            listOf(
                Vote(id = LongTypeId(1), userUid = userUid, placeId = LongTypeId(8), result = VoteResult.AGREE),
                Vote(id = LongTypeId(2), userUid = userUid, placeId = LongTypeId(9), result = VoteResult.AGREE),
                Vote(id = LongTypeId(3), userUid = userUid, placeId = LongTypeId(10), result = VoteResult.AGREE),
                Vote(id = LongTypeId(4), userUid = userUid, placeId = LongTypeId(10), result = VoteResult.AGREE),
            )

        given(roomQueryPort.findById(room.roomUid)).willReturn(room)
        given(scheduleQueryPort.findAllByRoomUid(room.roomUid)).willReturn(schedules)
        given(placeQueryPort.findAllByRoomUid(room.roomUid)).willReturn(places)
        given(voteQueryPort.findAllByPlaceIds(anyList())).willReturn(votes)

        val agreeCountPlaceId =
            votes
                .filter { it.result == VoteResult.AGREE }
                .groupingBy { it.placeId.getValue() }
                .eachCount()
        given(voteQueryPort.findAgreeCountByPlaceId(votes)).willReturn(agreeCountPlaceId)

        val coordinate1 = Coordinate(places[1].longitude, places[1].latitude)
        val coordinate2 = Coordinate(places[4].longitude, places[4].latitude)
        val coordinate3 = Coordinate(places[6].longitude, places[6].latitude)
        val coordinate4 = Coordinate(places[9].longitude, places[9].latitude)
        given(navigationClient.getDistance(coordinate1, coordinate2))
            .willReturn(Distance(100, 5))
        given(navigationClient.getDistance(coordinate2, coordinate3))
            .willReturn(Distance(100, 5))
        given(navigationClient.getDistance(coordinate3, coordinate4))
            .willReturn(Distance(100, 5))

        val updatedPlace = places[9].copy(confirmed = true)
        given(placeCommandPort.update(places[9].id, updatedPlace)).willReturn(updatedPlace)

        // when
        val result = courseService.retrieveCourse(room.roomUid)

        // then
        assertThat(result.places).hasSize(4)

        val placeIds = result.places.map { it.placeId }
        val expectedPlaceIds =
            listOf(places[1].id.getValue(), places[4].id.getValue(), places[6].id.getValue(), places[9].id.getValue())
        assertThat(placeIds).containsExactlyInAnyOrderElementsOf(expectedPlaceIds)
    }

    @Test
    fun `코스 장소에 위치 정보가 없으면 거리 정보는 null 로 반환한다`() {
        // given
        val room =
            Room(
                roomUid = UuidTypeId(UUID.randomUUID()),
                name = "사당에서 모이자",
                message = null,
                thumbnailLink = "https://test",
                password = Password("1234"),
                voteDeadline = LocalDateTime.now().minusDays(1),
            )

        val schedules =
            listOf(
                Schedule(
                    id = LongTypeId(1),
                    roomUid = room.roomUid,
                    name = "1차",
                    sequence = 1,
                    type = ScheduleType.DISH,
                ),
                Schedule(
                    id = LongTypeId(2),
                    roomUid = room.roomUid,
                    name = "2차",
                    sequence = 2,
                    type = ScheduleType.ALCOHOL,
                ),
            )

        val places =
            listOf(
                Place(
                    id = LongTypeId(1),
                    url = "주소를 입력하세요",
                    name = "",
                    thumbnailLinks = ThumbnailLinks(contents = null),
                    address = null,
                    phoneNumber = null,
                    starGrade = null,
                    origin = Origin.AVOCADO,
                    roomUid = room.roomUid,
                    scheduleId = schedules[0].id,
                    memo = null,
                    confirmed = true,
                    reviewCount = 0,
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
                    roomUid = room.roomUid,
                    scheduleId = schedules[1].id,
                    memo = null,
                    confirmed = true,
                    reviewCount = 0,
                    longitude = null,
                    latitude = null,
                ),
            )

        given(roomQueryPort.findById(room.roomUid)).willReturn(room)
        given(scheduleQueryPort.findAllByRoomUid(room.roomUid)).willReturn(schedules)
        given(placeQueryPort.findAllByRoomUid(room.roomUid)).willReturn(places)

        // when
        val result = courseService.retrieveCourse(room.roomUid)

        // then
        assertThat(result.places).hasSize(2)

        val manualPlace = result.places[1]
        assertThat(manualPlace.distance).isNull()
        assertThat(manualPlace.time).isNull()
    }
}
