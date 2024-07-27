package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.port.output.persistence.CourseQueryPort
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class CourseAdapter() : CourseQueryPort {
    override fun isCourseExist(roomUid: UUID): Boolean {
        // TODO: implement
        return false
    }
}
