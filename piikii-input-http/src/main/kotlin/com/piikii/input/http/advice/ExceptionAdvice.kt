package com.piikii.input.http.advice

import com.piikii.common.exception.PiikiiException
import com.piikii.input.http.error.ExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {

    @ExceptionHandler(PiikiiException::class)
    fun handlePiikiiException(piikiiException: PiikiiException): ResponseEntity<ExceptionResponse> {
        return ResponseEntity
            .status(piikiiException.statusCode)
            .body(
                ExceptionResponse(
                    message = piikiiException.defaultMessage,
                    cause = piikiiException.detailMessage,
                    timestamp = System.currentTimeMillis()
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ExceptionResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ExceptionResponse(
                    message = "예기치 못한 에러가 발생했습니다. : ${exception.message}",
                    cause = null,
                    timestamp = System.currentTimeMillis()
                )
            )
    }
}
