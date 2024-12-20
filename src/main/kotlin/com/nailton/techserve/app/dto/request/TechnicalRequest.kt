package com.nailton.techserve.app.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class TechnicalRequest(

    @field:NotBlank(message = "Nome não pode ser vazio")
    @field:Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    val name: String,

    @field:NotBlank(message = "Telefone não pode ser vazio")
//    @field:Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Número de telefone inválido")
    val telephone: String
)