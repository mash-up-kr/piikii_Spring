package com.piikii.input.http.handler

import com.piikii.common.exception.ExceptionResponseForm
import com.piikii.common.exception.PiikiiException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(PiikiiException::class)
    fun handlePiikiiException(piikiiException: PiikiiException): ResponseEntity<ExceptionResponseForm> {
        return ResponseEntity
            .status(piikiiException.statusCode)
            .body(
                ExceptionResponseForm(
                    message = piikiiException.message,
                    timestamp = System.currentTimeMillis()
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ExceptionResponseForm> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ExceptionResponseForm(
                    message = "예기치 못한 에러가 발생했습니다. : ${e.message}",
                    timestamp = System.currentTimeMillis()
                )
            )
    }
}
