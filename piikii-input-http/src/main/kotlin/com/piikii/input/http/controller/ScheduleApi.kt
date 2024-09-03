package com.piikii.input.http.controller

import com.piikii.application.domain.generic.UuidTypeId
import com.piikii.application.port.input.ScheduleUseCase
import com.piikii.application.port.input.dto.request.RegisterSchedulesRequest
import com.piikii.application.port.input.dto.response.SchedulesResponse
import com.piikii.input.http.controller.docs.ScheduleApiDocs
import com.piikii.input.http.controller.dto.ResponseForm
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Validated
@RestController
@RequestMapping("/v1/rooms/{roomUid}/schedules")
class ScheduleApi(
    private val scheduleUseCase: ScheduleUseCase,
) : ScheduleApiDocs {
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    override fun registerSchedules(
        @NotNull @PathVariable roomUid: UUID,
        @Valid @NotNull @RequestBody request: RegisterSchedulesRequest,
    ): ResponseForm<SchedulesResponse> {
        return ResponseForm(
            data = scheduleUseCase.registerSchedules(UuidTypeId(roomUid), request),
        )
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    override fun getSchedules(
        @NotNull @PathVariable roomUid: UUID,
    ): ResponseForm<SchedulesResponse> {
        return ResponseForm(
            data = scheduleUseCase.getSchedules(UuidTypeId(roomUid)),
        )
    }
}
