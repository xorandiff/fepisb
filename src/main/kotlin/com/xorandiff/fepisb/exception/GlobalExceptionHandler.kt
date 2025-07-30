package com.xorandiff.fepisb.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

data class ErrorResponse(val status: Int, val message: String)

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUsernameNotFound(ex: UsernameNotFoundException): ErrorResponse =
        ErrorResponse(status = HttpStatus.NOT_FOUND.value(),
            message = ex.message.orEmpty())

    @ExceptionHandler(RateLimitExceededException::class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    fun handleRateLimit(ex: RateLimitExceededException): ErrorResponse =
        ErrorResponse(status = HttpStatus.TOO_MANY_REQUESTS.value(),
            message = ex.message.orEmpty())
}