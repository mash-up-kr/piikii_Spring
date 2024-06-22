package com.piikii.input.http.docs

import com.piikii.application.port.input.room.dto.request.RoomSaveRequestForm
import com.piikii.application.port.input.room.dto.request.RoomUpdateRequestForm
import com.piikii.application.port.input.room.dto.request.VoteGenerateRequestForm
import com.piikii.application.port.input.room.dto.response.RoomGetResponseForm
import com.piikii.application.port.input.room.dto.response.RoomSaveResponseForm
import com.piikii.input.http.generic.ResponseForm
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@Tag(name = "SourcePlaceApi", description = "SourcePlace Api 입니다.")
interface RoomApiDocs {
    @Operation(summary = "make room API", description = "방 생성 요청 메서드")
    @ApiResponses(value = [ApiResponse(responseCode = "201", description = "CREATED success")])
    fun make(request: RoomSaveRequestForm): ResponseForm<RoomSaveResponseForm>

    @Operation(summary = "modify room information API", description = "방 정보 수정 요청 메서드")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "UPDATED success")])
    fun modifyInformation(
        request: RoomUpdateRequestForm,
        @PathVariable(name = "방 id") roomId: Long,
    ): ResponseForm<Any>

    @Operation(summary = "remove room API", description = "방 삭제 요청 메서드")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "DELETED success")])
    fun remove(
        @PathVariable(name = "방 id") roomId: Long,
    ): ResponseForm<Any>

    @Operation(summary = "search room API", description = "방 조회 요청 메서드")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "GET success")])
    fun search(
        @PathVariable(name = "방 id") roomId: UUID,
    ): ResponseForm<RoomGetResponseForm>

    @Operation(summary = "generate vote of room API", description = "방 투표 생성 요청 메서드")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "CREATED success")])
    fun addVoteDeadline(request: VoteGenerateRequestForm): ResponseForm<Any>
}
