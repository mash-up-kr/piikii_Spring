package com.piikii.application.domain.roomcategory

import com.piikii.application.port.input.roomcategory.RoomCategoryUseCase
import com.piikii.application.port.input.roomcategory.dto.request.RoomCategoriesCreateRequest
import com.piikii.application.port.input.roomcategory.dto.request.RoomCategoryIdsRequest
import com.piikii.application.port.input.roomcategory.dto.response.RoomCategoriesReadResponse
import com.piikii.application.port.output.persistence.RoomCategoryCommandPort
import com.piikii.application.port.output.persistence.RoomCategoryQueryPort
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RoomCategoryService(
    private val roomCategoryQueryPort: RoomCategoryQueryPort,
    private val roomCategoryCommandPort: RoomCategoryCommandPort,
) : RoomCategoryUseCase {
    override fun createRoomCategories(
        roomId: UUID,
        request: RoomCategoriesCreateRequest,
    ) {
        roomCategoryCommandPort.saveRoomCategories(request.toDomains(roomId))
    }

    override fun readRoomCategories(roomId: UUID): RoomCategoriesReadResponse {
        return RoomCategoriesReadResponse.from(
            roomCategoryQueryPort.findRoomCategoriesByRoomId(roomId),
        )
    }

    override fun deleteRoomCategories(request: RoomCategoryIdsRequest) {
        roomCategoryCommandPort.deleteRoomCategories(request.categoryIds)
    }
}
