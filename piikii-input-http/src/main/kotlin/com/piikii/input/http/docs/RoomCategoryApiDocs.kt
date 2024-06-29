package com.piikii.input.http.docs

import com.piikii.application.port.input.roomcategory.dto.request.RoomCategoriesCreateRequest
import com.piikii.application.port.input.roomcategory.dto.request.RoomCategoryIdsRequest
import com.piikii.application.port.input.roomcategory.dto.response.RoomCategoriesReadResponse
import com.piikii.input.http.generic.ResponseForm
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@Tag(name = "RoomCategoryApi", description = "RoomCategory Api 입니다.")
interface RoomCategoryApiDocs {
    @Operation(summary = "방 카테고리 생성 api", description = "방 카테고리를 생성합니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "201", description = "CREATED success")])
    fun createRoomCategories(request: RoomCategoriesCreateRequest): ResponseForm<Unit>

    @Operation(summary = "방 카테고리 조회 api", description = "방 카테고리를 조회합니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "OK success")])
    fun readRoomCategories(
        @PathVariable(name = "카테고리를 조회할 방 uuid") roomId: UUID,
    ): ResponseForm<RoomCategoriesReadResponse>

    @Operation(summary = "방 카테고리 삭제 api", description = "방 카테고리를 삭제합니다.")
    @ApiResponses(value = [ApiResponse(responseCode = "204", description = "NO_CONTENT success")])
    fun deleteRoomCategories(request: RoomCategoryIdsRequest): ResponseForm<Unit>
}
