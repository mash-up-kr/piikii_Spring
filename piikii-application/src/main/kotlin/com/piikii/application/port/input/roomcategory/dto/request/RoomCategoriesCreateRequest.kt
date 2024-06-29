package com.piikii.application.port.input.roomcategory.dto.request

import com.piikii.application.domain.roomcategory.PlaceCategory
import com.piikii.application.domain.roomcategory.RoomCategory
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class RoomCategoriesCreateRequest(
    @Schema(description = "카테고리를 추가하는 방의 uuid")
    val roomId: UUID,
    @Schema(description = "추가할 방 카테고리 목록")
    val roomCategories: List<RoomCategoryRequest>,
) {
    fun toDomains(): List<RoomCategory> {
        return roomCategories.map { it.toDomain(this.roomId) }.toList()
    }

    data class RoomCategoryRequest(
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
}
