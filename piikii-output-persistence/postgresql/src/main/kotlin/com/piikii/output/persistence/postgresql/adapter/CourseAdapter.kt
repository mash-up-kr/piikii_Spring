package com.piikii.output.persistence.postgresql.adapter

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.port.output.persistence.CourseQueryPort
import org.springframework.stereotype.Repository

@Repository
class CourseAdapter() : CourseQueryPort {
    override fun isCourseExist(roomUid: UuidTypeId): Boolean {
        // TODO: implement
        return false
    }
}
