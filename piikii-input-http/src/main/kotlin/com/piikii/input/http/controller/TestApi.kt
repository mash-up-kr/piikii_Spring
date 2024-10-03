package com.piikii.input.http.controller

import com.piikii.input.http.aspect.PreventDuplicateRequest
import com.piikii.input.http.controller.dto.ResponseForm
import io.micrometer.core.annotation.Counted
import io.micrometer.core.aop.CountedAspect
import io.micrometer.core.instrument.MeterRegistry
import jakarta.validation.constraints.NotNull
import java.util.UUID
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


@Validated
@RestController
@RequestMapping("/api/v1/test")
class TestApi(
    private val testService: TestService
) {
    @PreventDuplicateRequest("#uuid")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/entity/{uuid}")
    fun testDuplicateSave(
        @NotNull @PathVariable uuid: UUID,
    ): ResponseForm<Unit> {
        testService.testSaveBusinessLogic(uuid)
        Thread.sleep(3_000)
        return ResponseForm.EMPTY_RESPONSE;
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
