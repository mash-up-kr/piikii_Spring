package com.piikii.input.http.controller

import com.piikii.input.http.aspect.PreventDuplicateRequest
import com.piikii.input.http.aspect.RateLimiter
import com.piikii.input.http.controller.dto.ResponseForm
import io.micrometer.core.annotation.Counted
import io.micrometer.core.aop.CountedAspect
import io.micrometer.core.instrument.MeterRegistry
import jakarta.validation.constraints.NotNull
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.temporal.ChronoUnit
import java.util.UUID


@Validated
@RestController
@RequestMapping("/api/v1/test")
class TestApi(
    private val testService: TestService
) {


    @PreventDuplicateRequest(key = "#uuid", timeUnit = ChronoUnit.SECONDS, amount = 10)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/entity/preventDuplicate/concurrent/{uuid}")
    fun testDuplicateSaveConcurrent(
        @NotNull @PathVariable uuid: UUID,
    ): ResponseForm<Unit> {
        testService.testSaveBusinessLogic(uuid)
        Thread.sleep(3_000)
        return ResponseForm.EMPTY_RESPONSE
    }

    @PreventDuplicateRequest("#uuid")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/entity/preventDuplicate/{uuid}")
    fun testDuplicateSave(
        @NotNull @PathVariable uuid: UUID,
    ): ResponseForm<Unit> {
        testService.testSaveBusinessLogic(uuid)
        Thread.sleep(3_000)
        return ResponseForm.EMPTY_RESPONSE
    }

    @RateLimiter(key = "#uuid", maxRequestCount = 5, timeUnit = ChronoUnit.HOURS, amount = 1)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/entity/RateLimit/{uuid}")
    fun testRateLimitSave(
        @NotNull @PathVariable uuid: UUID,
    ): ResponseForm<Unit> {
        testService.testSaveBusinessLogic(uuid)
        return ResponseForm.EMPTY_RESPONSE
    }


    @RateLimiter(key = "#uuid", maxRequestCount = 3, timeUnit = ChronoUnit.SECONDS, amount = 5)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/entity/RateLimit/{uuid}/short")
    fun testRateLimitSaveShort(
        @NotNull @PathVariable uuid: UUID,
    ): ResponseForm<Unit> {
        testService.testSaveBusinessLogic(uuid)
        return ResponseForm.EMPTY_RESPONSE
    }
}

@Service
class TestService {

    @Counted("test.save.entity")
    fun testSaveBusinessLogic(uuid: UUID) {
        println("uuid = ${uuid}")
    }

}

@Configuration
class TestConfig {

    @Bean
    fun countedAspect(meterRegistry: MeterRegistry): CountedAspect {
        return CountedAspect(meterRegistry)
    }

}
