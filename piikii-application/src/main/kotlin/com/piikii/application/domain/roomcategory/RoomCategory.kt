package com.piikii.application.domain.roomcategory

import java.util.UUID

class RoomCategory(
    val id: Long? = null,
    val roomId: UUID,
    val name: String,
    val category: PlaceCategory,
    val sequence: Int,
)
