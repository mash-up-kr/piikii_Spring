package com.piikii.application.port.input.roomcategory.dto.response

import com.piikii.application.domain.roomcategory.PlaceCategory
import com.piikii.application.domain.roomcategory.RoomCategory
import io.swagger.v3.oas.annotations.media.Schema

data class RoomCategoriesReadResponse(
    @Schema(description = "방 카테고리 정보 목록")
    val roomCategories: List<RoomCategoryResponse>,
) {
    companion object {
        fun from(roomCategories: List<RoomCategory>): RoomCategoriesReadResponse {
            return RoomCategoriesReadResponse(
                roomCategories.map { RoomCategoryResponse(it) }.toList(),
            )
        }
    }
}

data class RoomCategoryResponse(
    @Schema(description = "방 카테고리의 id")
    val roomCategoryId: Long,
    @Schema(description = "방 카테고리의 이름")
    val name: String,
    @Schema(description = "방 카테고리의 카테고리")
    val category: PlaceCategory,
    @Schema(description = "방 카테고리의 순서")
    val sequence: Int,
) {
    constructor(roomCategory: RoomCategory) : this (
        roomCategoryId = roomCategory.id!!,
        name = roomCategory.name,
        category = roomCategory.category,
        sequence = roomCategory.sequence,
    )
}
