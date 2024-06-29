package com.piikii.input.http.controller

import com.piikii.application.port.input.roomcategory.RoomCategoryUseCase
import com.piikii.application.port.input.roomcategory.dto.request.RoomCategoriesCreateRequest
import com.piikii.application.port.input.roomcategory.dto.request.RoomCategoryIdsRequest
import com.piikii.application.port.input.roomcategory.dto.response.RoomCategoriesReadResponse
import com.piikii.input.http.docs.RoomCategoryApiDocs
import com.piikii.input.http.generic.ResponseForm
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/rooms/{roomId}/categories")
class RoomCategoryApi(
    private val roomCategoryUseCase: RoomCategoryUseCase,
) : RoomCategoryApiDocs {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    override fun createRoomCategories(
        @PathVariable roomId: UUID,
        @RequestBody request: RoomCategoriesCreateRequest,
    ): ResponseForm<Unit> {
        roomCategoryUseCase.createRoomCategories(roomId, request)
        return ResponseForm(
            message = SUCCESS_CREATE_ROOM_CATEGORIES_MESSAGE,
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    override fun readRoomCategories(
        @PathVariable roomId: UUID,
    ): ResponseForm<RoomCategoriesReadResponse> {
        return ResponseForm(
            data = roomCategoryUseCase.readRoomCategories(roomId),
            message = SUCCESS_READ_ROOM_CATEGORIES_MESSAGE,
        )
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    override fun deleteRoomCategories(
        @PathVariable roomId: UUID,
        @RequestBody request: RoomCategoryIdsRequest,
    ): ResponseForm<Unit> {
        roomCategoryUseCase.deleteRoomCategories(request)
        return ResponseForm(
            message = SUCCESS_DELETE_ROOM_CATEGORIES_MESSAGE,
        )
    }

    companion object {
        const val SUCCESS_CREATE_ROOM_CATEGORIES_MESSAGE = "방 카테고리 목록 생성 성공"
        const val SUCCESS_READ_ROOM_CATEGORIES_MESSAGE = "방 카테고리 목록 조회 성공"
        const val SUCCESS_DELETE_ROOM_CATEGORIES_MESSAGE = "방 카테고리 목록 삭제 성공"
    }
}
