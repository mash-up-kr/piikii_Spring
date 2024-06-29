package com.piikii.input.http.controller

import com.piikii.application.port.input.ScheduleUseCase
import com.piikii.application.port.input.dto.request.CreateSchedulesRequest
import com.piikii.application.port.input.dto.request.DeleteSchedulesRequest
import com.piikii.application.port.input.dto.response.SchedulesResponse
import com.piikii.input.http.controller.docs.ScheduleApiDocs
import com.piikii.input.http.controller.dto.ResponseForm
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
@RequestMapping("/v1/rooms/{roomId}/schedules")
class ScheduleApi(
    private val scheduleUseCase: ScheduleUseCase,
) : ScheduleApiDocs {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    override fun createSchedules(
        @PathVariable roomId: UUID,
        @RequestBody request: CreateSchedulesRequest,
    ): ResponseForm<Unit> {
        scheduleUseCase.createSchedules(roomId, request)
        return ResponseForm.EMPTY_RESPONSE
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    override fun getSchedules(
        @PathVariable roomId: UUID,
    ): ResponseForm<SchedulesResponse> {
        return ResponseForm(
            data = scheduleUseCase.getSchedules(roomId),
        )
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    override fun deleteSchedules(
        @PathVariable roomId: UUID,
        @RequestBody request: DeleteSchedulesRequest,
    ): ResponseForm<Unit> {
        scheduleUseCase.deleteSchedules(request)
        return ResponseForm.EMPTY_RESPONSE
    }
}
