package com.piikii.application.port.input.roomcategory

import com.piikii.application.port.input.roomcategory.dto.request.RoomCategoriesCreateRequest
import com.piikii.application.port.input.roomcategory.dto.request.RoomCategoryIdsRequest
import com.piikii.application.port.input.roomcategory.dto.response.RoomCategoriesReadResponse
import java.util.UUID

interface RoomCategoryUseCase {
    fun createRoomCategories(
        roomId: UUID,
        request: RoomCategoriesCreateRequest,
    )

    fun readRoomCategories(roomId: UUID): RoomCategoriesReadResponse

    fun deleteRoomCategories(request: RoomCategoryIdsRequest)
}
