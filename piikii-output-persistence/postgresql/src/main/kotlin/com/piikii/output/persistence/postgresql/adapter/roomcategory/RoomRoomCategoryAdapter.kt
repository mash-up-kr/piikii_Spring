package com.piikii.output.persistence.postgresql.adapter.roomcategory

import com.piikii.application.domain.roomcategory.RoomCategory
import com.piikii.application.port.output.persistence.RoomCategoryQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.persistence.postgresql.persistence.repository.RoomCategoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
@Transactional(readOnly = true)
class RoomRoomCategoryAdapter(
    private val roomCategoryRepository: RoomCategoryRepository,
) : RoomCategoryQueryPort {
    override fun retrieveById(categoryId: Long): RoomCategory {
        return roomCategoryRepository.findByIdOrNull(categoryId)?.toDomain() ?: throw PiikiiException(
            exceptionCode = ExceptionCode.NOT_FOUNDED,
            detailMessage = "categoryId : $categoryId",
        )
    }

    override fun retrieveAllByRoomId(roomId: UUID): List<RoomCategory> {
        return roomCategoryRepository.findByRoomId(roomId).map { it.toDomain() }
    }
}
