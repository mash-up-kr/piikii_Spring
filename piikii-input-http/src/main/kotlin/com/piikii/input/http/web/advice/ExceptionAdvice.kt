package com.piikii.input.http.web.advice

import com.piikii.common.exception.PiikiiException
import com.piikii.common.logutil.SlackHookLogger
import com.piikii.common.logutil.SystemLogger.logger
import com.piikii.input.http.controller.dto.response.ExceptionResponse
import jakarta.validation.ConstraintDeclarationException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class ExceptionAdvice(
    private val slackHookLogger: SlackHookLogger,
) {
    @ExceptionHandler(PiikiiException::class)
    fun handlePiikiiException(piikiiException: PiikiiException): ResponseEntity<ExceptionResponse> {
        logger.error(piikiiException) { "Occurred PiikiiException" }
        return ResponseEntity
            .status(piikiiException.statusCode)
            .body(
                ExceptionResponse(
                    message = piikiiException.defaultMessage,
                    cause = piikiiException.detailMessage,
                    timestamp = System.currentTimeMillis(),
                ),
            )
    }

    @ExceptionHandler(
        MethodArgumentNotValidException::class,
        ConstraintViolationException::class,
        ConstraintDeclarationException::class,
    )
    fun handleValidationExceptions(exception: Exception): ResponseEntity<ExceptionResponse> {
        logger.error(exception) { "RequestBody Validation Failure" }

        val cause =
            when (exception) {
                is MethodArgumentNotValidException -> {
                    exception.bindingResult.fieldErrors.joinToString(", ") { fieldError ->
                        "${fieldError.field}: ${fieldError.defaultMessage}"
                    }
                }

                is ConstraintViolationException -> {
                    exception.constraintViolations.joinToString(", ") { violation ->
                        "${violation.propertyPath}: ${violation.message}"
                    }
                }

                else -> {
                    "Cause is chaos [${exception.message}]"
                }
            }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ExceptionResponse(
                    message = "요청 형식이 잘못되었습니다 (Request Validation Failure)",
                    cause = cause,
                    timestamp = System.currentTimeMillis(),
                ),
            )
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(
        noResourceFoundException: NoResourceFoundException,
    ): ResponseEntity<ExceptionResponse> {
        logger.error(noResourceFoundException) { "Request Resources Validation Failure" }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ExceptionResponse(
                    message = "요청 경로가 잘못되었습니다 (Request Resources is incorrect)",
                    cause = noResourceFoundException.resourcePath,
                    timestamp = System.currentTimeMillis(),
                ),
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ExceptionResponse> {
        logger.error(exception) { "Occurred Exception" }
        slackHookLogger.send(exception.localizedMessage)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ExceptionResponse(
                    message = "예기치 못한 에러가 발생했습니다.",
                    cause = null,
                    timestamp = System.currentTimeMillis(),
                ),
            )
    }
}
