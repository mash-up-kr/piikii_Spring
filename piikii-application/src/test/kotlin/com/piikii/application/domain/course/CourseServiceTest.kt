package com.piikii.application.domain.course

import com.piikii.application.domain.fixture.PlaceFixture
import com.piikii.application.domain.fixture.RoomFixture
import com.piikii.application.domain.fixture.ScheduleFixture
import com.piikii.application.domain.fixture.VoteFixture
import com.piikii.application.domain.vote.VoteResult
import com.piikii.application.port.output.cache.CourseCachePort
import com.piikii.application.port.output.persistence.CourseQueryPort
import com.piikii.application.port.output.persistence.PlaceCommandPort
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import com.piikii.application.port.output.persistence.VoteQueryPort
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
    lateinit var courseCachePort: CourseCachePort

    @Mock
    lateinit var courseQueryPort: CourseQueryPort

    @Test
    fun `VoteDeadline 이 현재보다 이후이면 Exception 이 발생한다`() {
        // given
        val room = RoomFixture.create().voteDeadline(LocalDateTime.now().plusDays(1)).build()

        given(roomQueryPort.findById(room.roomUid)).willReturn(room)

        // when & then
        assertThatThrownBy { courseService.retrieveCourse(room.roomUid) }
            .isExactlyInstanceOf(PiikiiException::class.java)
            .hasMessageContaining(CourseService.VOTE_NOT_END)
    }

    @Test
    fun `투표 시작 전이면 Exception 이 발생한다`() {
        // given
        val room = RoomFixture.create().build()

        given(roomQueryPort.findById(room.roomUid)).willReturn(room)

        // when & then
        assertThatThrownBy { courseService.retrieveCourse(room.roomUid) }
            .isExactlyInstanceOf(PiikiiException::class.java)
            .hasMessageContaining(CourseService.VOTE_NOT_END)
    }

    @Test
    fun `Room 의 각 Schedule 중 confirmed 된 Place 목록을 조회한다`() {
        // given
        val room = RoomFixture.create().voteDeadline(LocalDateTime.now().minusDays(1)).build()

        val schedules =
            listOf(
                ScheduleFixture.create().id(1L).roomUid(room.roomUid).build(),
                ScheduleFixture.create().id(2L).roomUid(room.roomUid).build(),
            )

        val places =
            listOf(
                PlaceFixture.create()
                    .id(1L)
                    .roomUid(room.roomUid)
                    .scheduleId(schedules[0].id)
                    .confirmed(true)
                    .longitude(126.9246033)
                    .latitude(33.45241976)
                    .build(),
                PlaceFixture.create()
                    .id(2L)
                    .roomUid(room.roomUid)
                    .scheduleId(schedules[1].id)
                    .longitude(126.9246033)
                    .latitude(33.45241976)
                    .build(),
                PlaceFixture.create()
                    .id(3L)
                    .roomUid(room.roomUid)
                    .scheduleId(schedules[1].id)
                    .longitude(126.9246033)
                    .latitude(33.45241977)
                    .build(),
            )

        val votes =
            listOf(
                VoteFixture.create().placeId(places[1].id).result(VoteResult.AGREE).build(),
                VoteFixture.create().placeId(places[2].id).result(VoteResult.AGREE).build(),
                VoteFixture.create().placeId(places[2].id).result(VoteResult.AGREE).build(),
            )

        given(roomQueryPort.findById(room.roomUid)).willReturn(room)
        given(scheduleQueryPort.findAllByRoomUid(room.roomUid)).willReturn(schedules)
        given(placeQueryPort.findAllByRoomUid(room.roomUid)).willReturn(places)
        given(voteQueryPort.findAllByPlaceIds(anyList())).willReturn(votes)
        given(voteQueryPort.findAgreeCountByPlaceId(votes))
            .willReturn(
                mapOf(
                    2L to 1,
                    3L to 2,
                ),
            )

        val coursePlace =
            CoursePlace.from(
                schedule = schedules[0],
                place = places[0],
                coordinate = Coordinate(places[0].longitude, places[0].latitude),
                distance = Distance.EMPTY,
            )

        given(courseCachePort.getDistance(null, places[0]))
            .willReturn(Distance.EMPTY)
        given(courseCachePort.getDistance(coursePlace, places[2]))
            .willReturn(Distance(100, 5))

        val updatedPlace = places[2].copy(confirmed = true)
        given(placeCommandPort.update(places[2].id, updatedPlace)).willReturn(updatedPlace)

        // when
        val result = courseService.retrieveCourse(room.roomUid)

        // then
        assertThat(result.places).hasSize(2)

        val placeIds = result.places.map { it.placeId }
        val expectedPlaceIds = listOf(places[0].id.getValue(), places[2].id.getValue())
        assertThat(placeIds).containsExactlyInAnyOrderElementsOf(expectedPlaceIds)
    }

    @Test
    fun `코스 장소에 위치 정보가 없으면 거리 정보는 null 로 반환한다`() {
        // given
        val room = RoomFixture.create().voteDeadline(LocalDateTime.now().minusDays(1)).build()

        val schedules =
            listOf(
                ScheduleFixture.create().id(1L).roomUid(room.roomUid).build(),
                ScheduleFixture.create().id(2L).roomUid(room.roomUid).build(),
            )

        val places =
            listOf(
                PlaceFixture.create()
                    .id(1L)
                    .roomUid(room.roomUid)
                    .scheduleId(schedules[0].id)
                    .confirmed(true)
                    .longitude(126.9246033)
                    .latitude(33.45241976)
                    .build(),
                PlaceFixture.create()
                    .id(2L)
                    .roomUid(room.roomUid)
                    .scheduleId(schedules[1].id)
                    .confirmed(true)
                    .build(),
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
