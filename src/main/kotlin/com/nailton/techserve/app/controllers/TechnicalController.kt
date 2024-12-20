package com.nailton.techserve.app.controllers

import com.nailton.techserve.app.dto.request.TechnicalRequest
import com.nailton.techserve.app.dto.response.TechnicalResponse
import com.nailton.techserve.intrastructure.services.TechnicalService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/technicals")
class TechnicalController(private val technicalService: TechnicalService) {

    @PostMapping
    fun createTechnical(@RequestBody @Valid request: TechnicalRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<TechnicalResponse> {

        // Chama o serviço para criar o técnico
        val response = technicalService.createTechnical(request)

        // Cria o URI do novo recurso utilizando o UriComponentsBuilder
        val uri = uriBuilder.path("/technicals/${response.id}").build().toUri()

        // Retorna a resposta com status 201 (Created) e o URI do novo recurso
        return ResponseEntity.created(uri).body(response)
    }




}