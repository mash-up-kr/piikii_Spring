package com.piikii.application.domain.course

import com.piikii.application.port.input.CourseUseCase
import com.piikii.application.port.input.dto.response.CourseResponse
import com.piikii.application.port.output.persistence.RoomQueryPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CourseService(
    private val roomQueryPort: RoomQueryPort
) : CourseUseCase {
    override fun isCourseExist(roomId: UUID): Boolean {
        return roomQueryPort.retrieve(roomId).isCourseCreated
    }

    override fun createCourse(roomId: UUID): CourseResponse {
        TODO("Not yet implemented")
    }
}
