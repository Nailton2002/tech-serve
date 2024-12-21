package com.nailton.techserve.app.controllers

import com.nailton.techserve.intrastructure.exceptions.BusinessRuleException
import com.nailton.techserve.intrastructure.exceptions.DataIntegrityException
import com.nailton.techserve.intrastructure.exceptions.DatabaseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleTypeMismatchException(ex: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResponse> {
        val errorMessage = "O valor '${ex.value}' não é válido para o campo '${ex.name}'."
        val errorResponse = ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }


    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno: ${ex.message}")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
    }

    //CLASSES PERSONALIZADAS
    @ExceptionHandler(BusinessRuleException::class)
    fun handleBusinessRuleException(ex: BusinessRuleException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.message ?: "Regra de negócio violada")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
    }

    //CLASSES PERSONALIZADAS
    @ExceptionHandler(DataIntegrityException::class)
    fun handleDataIntegrityException(ex: DataIntegrityException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.CONFLICT.value(), ex.message ?: "Violação de integridade de dados")
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error)
    }

    //CLASSES PERSONALIZADAS
    @ExceptionHandler(DatabaseException::class)
    fun handleDatabaseException(ex: DatabaseException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.message ?: "Erro no banco de dados")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
    }

}

data class ErrorResponse(
    val status: Int,
    val message: String
)