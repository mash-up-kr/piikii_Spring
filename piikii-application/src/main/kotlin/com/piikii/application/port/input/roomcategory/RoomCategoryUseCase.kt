package com.piikii.application.port.input.roomcategory

import com.piikii.application.port.input.roomcategory.dto.request.RoomCategoriesCreateRequest
import com.piikii.application.port.input.roomcategory.dto.request.RoomCategoryIdsRequest
import com.piikii.application.port.input.roomcategory.dto.response.RoomCategoriesReadResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID

interface RoomCategoryUseCase {
    fun createRoomCategories(
        @RequestBody request: RoomCategoriesCreateRequest,
    )

    fun readRoomCategories(
        @PathVariable roomId: UUID,
    ): RoomCategoriesReadResponse

    fun deleteRoomCategories(
        @RequestBody request: RoomCategoryIdsRequest,
    )
}
