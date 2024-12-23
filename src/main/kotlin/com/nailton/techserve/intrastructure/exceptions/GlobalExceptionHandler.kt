package com.nailton.techserve.intrastructure.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {

    // Classe personalizada de tratamento de exceção
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse(status = HttpStatus.NOT_FOUND.value(), message = ex.message ?: "Recurso não encontrado")
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
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