package com.piikii.application.domain.course

import com.piikii.application.domain.place.Place
import com.piikii.application.port.input.CourseUseCase
import com.piikii.application.port.input.dto.response.CourseResponse
import com.piikii.application.port.output.persistence.PlaceQueryPort
import com.piikii.application.port.output.persistence.RoomQueryPort
import com.piikii.application.port.output.persistence.ScheduleQueryPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CourseService(
    private val roomQueryPort: RoomQueryPort,
    private val scheduleQueryPort: ScheduleQueryPort,
    private val placeQueryPort: PlaceQueryPort,
) : CourseUseCase {
    override fun isCourseExist(roomId: UUID): Boolean {
        return roomQueryPort.retrieve(roomId).isCourseCreated
    }

    override fun createCourse(roomId: UUID): CourseResponse {
        val schedules = scheduleQueryPort.findSchedulesByRoomId(roomId)
        return CourseResponse.from(
            room = roomQueryPort.retrieve(roomId),
            places = schedules.map { choiceVoteResultPlaceInSchedule(it.id!!) },
        )
    }

    private fun choiceVoteResultPlaceInSchedule(scheduleId: Long): Place {
        return placeQueryPort.findMostPopularPlaceByScheduleId(scheduleId)
    }
}
