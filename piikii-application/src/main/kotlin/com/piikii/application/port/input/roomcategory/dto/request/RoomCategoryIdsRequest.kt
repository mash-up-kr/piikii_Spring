package com.piikii.application.port.input.roomcategory.dto.request

import io.swagger.v3.oas.annotations.media.Schema

data class RoomCategoryIdsRequest(
    @Schema(description = "삭제할 방 카테고리 id 목록")
    val categoryIds: List<Long>,
)
