package com.nailton.techserve.app.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException) = ErrorResponse(
        status = HttpStatus.NOT_FOUND.value(),
        message = ex.message.toString()
    )
}

data class ErrorResponse(val status: Int, val message: String)

class ResourceNotFoundException(message: String) : RuntimeException(message)