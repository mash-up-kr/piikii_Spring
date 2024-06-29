package com.piikii.output.persistence.postgresql.adapter.roomcategory

import com.piikii.application.domain.roomcategory.RoomCategory
import com.piikii.application.port.output.persistence.RoomCategoryCommandPort
import com.piikii.application.port.output.persistence.RoomCategoryQueryPort
import com.piikii.common.exception.ExceptionCode
import com.piikii.common.exception.PiikiiException
import com.piikii.output.persistence.postgresql.persistence.entity.RoomCategoryEntity
import com.piikii.output.persistence.postgresql.persistence.repository.RoomCategoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
@Transactional(readOnly = true)
class RoomCategoryAdapter(
    private val roomCategoryRepository: RoomCategoryRepository,
) : RoomCategoryCommandPort, RoomCategoryQueryPort {
    @Transactional
    override fun saveRoomCategories(roomCategories: List<RoomCategory>) {
        roomCategoryRepository.saveAll(
            roomCategories.map { RoomCategoryEntity(it) },
        )
    }

    @Transactional
    override fun deleteRoomCategories(roomCategoryIds: List<Long>) {
        roomCategoryRepository.deleteAll(
            roomCategoryIds.map { findRoomCategoryById(it) },
        )
    }

    override fun findRoomCategoriesByRoomId(roomId: UUID): List<RoomCategory> {
        return roomCategoryRepository.findByRoomIdOrderBySequenceAsc(roomId).map { it.toDomain() }
    }

    private fun findRoomCategoryById(id: Long): RoomCategoryEntity {
        return roomCategoryRepository.findByIdOrNull(id)
            ?: throw PiikiiException(
                exceptionCode = ExceptionCode.NOT_FOUNDED,
                detailMessage = "roomCategoryId: $id",
            )
    }
}
