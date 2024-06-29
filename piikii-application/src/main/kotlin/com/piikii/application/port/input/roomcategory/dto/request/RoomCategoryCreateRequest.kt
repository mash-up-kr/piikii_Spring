package com.piikii.application.port.input.roomcategory.dto.request

import com.piikii.application.domain.roomcategory.PlaceCategory
import com.piikii.application.domain.roomcategory.RoomCategory
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class RoomCategoryCreateRequest(
    @Schema(description = "방 카테고리의 이름")
    val name: String,
    @Schema(description = "방 카테고리의 카테고리")
    val category: PlaceCategory,
    @Schema(description = "방 카테고리의 순서")
    val sequence: Int,
) {
    fun toDomain(roomId: UUID): RoomCategory {
        return RoomCategory(
            roomId = roomId,
            name = this.name,
            category = this.category,
            sequence = this.sequence,
        )
    }
}
