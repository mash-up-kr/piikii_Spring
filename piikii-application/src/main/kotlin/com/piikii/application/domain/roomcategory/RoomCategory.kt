package com.piikii.application.domain.roomcategory

import com.piikii.application.domain.generic.PlaceCategory
import java.util.UUID

data class RoomCategory(
    val id: Long? = null,
    val roomId: UUID,
    val name: String,
    val placeCategory: PlaceCategory,
    val sequence: Int,
)
