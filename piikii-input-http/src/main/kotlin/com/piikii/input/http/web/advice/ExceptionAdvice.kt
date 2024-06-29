package com.piikii.input.http.web.advice

import com.piikii.common.exception.PiikiiException
import com.piikii.common.logutil.SystemLogger.logger
import com.piikii.input.http.controller.dto.response.ExceptionResponse
import jakarta.validation.ConstraintDeclarationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {
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

    @ExceptionHandler(ConstraintDeclarationException::class)
    fun handleConstraintDeclarationException(
        exception: ConstraintDeclarationException,
    ): ResponseEntity<ExceptionResponse> {
        logger.error(exception) { "RequestBody Validation Failure" }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ExceptionResponse(
                    message = "요청 형식이 잘못됬습니다 (Request Validation Failure)",
                    cause = exception.message,
                    timestamp = System.currentTimeMillis(),
                ),
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ExceptionResponse> {
        logger.error(exception) { "Occurred Exception" }
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
