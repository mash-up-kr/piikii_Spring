package com.piikii.input.http.controller

import com.piikii.application.port.input.ScheduleUseCase
import com.piikii.application.port.input.dto.request.RegisterSchedulesRequest
import com.piikii.input.http.controller.docs.ScheduleApiDocs
import com.piikii.input.http.controller.dto.ResponseForm
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/rooms/{roomUid}/schedules")
class ScheduleApi(
    private val scheduleUseCase: ScheduleUseCase,
) : ScheduleApiDocs {
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    override fun registerSchedules(
        @PathVariable roomUid: UUID,
        @RequestBody request: RegisterSchedulesRequest,
    ): ResponseForm<Unit> {
        scheduleUseCase.registerSchedules(roomUid, request)
        return ResponseForm.EMPTY_RESPONSE
    }
}
