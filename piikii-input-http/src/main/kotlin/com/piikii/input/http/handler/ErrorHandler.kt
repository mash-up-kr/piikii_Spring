package com.piikii.input.http.handler

import com.piikii.common.error.PiikiiError
import com.piikii.input.http.error.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(PiikiiError::class)
    fun handlePiikiiException(piikiiError: PiikiiError): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(piikiiError.statusCode)
            .body(
                ErrorResponse(
                    message = piikiiError.defaultMessage,
                    cause = piikiiError.detailMessage,
                    timestamp = System.currentTimeMillis()
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    message = "예기치 못한 에러가 발생했습니다. : ${e.message}",
                    cause = null,
                    timestamp = System.currentTimeMillis()
                )
            )
    }
}
