package com.nailton.techserve.app.controllers

import com.nailton.techserve.intrastructure.services.TechnicalService
import com.nailton.techserve.app.dto.response.TechnicalResponse
import com.nailton.techserve.app.dto.request.TechnicalRequest
import org.springframework.web.util.UriComponentsBuilder
import org.springframework.http.ResponseEntity
import jakarta.validation.Valid
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/technicals")
class TechnicalController(private val technicalService: TechnicalService) {

    // Create (POST /technicals)
    @PostMapping
    fun createTechnical(@RequestBody @Valid request: TechnicalRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<TechnicalResponse> {

        // Chama o serviço para criar o técnico
        val response = technicalService.createTechnical(request)

        // Cria o URI do novo recurso utilizando o UriComponentsBuilder
        val uri = uriBuilder.path("/technicals/${response.id}").build().toUri()

        // Retorna a resposta com status 201 (Created) e o URI do novo recurso
        return ResponseEntity.created(uri).body(response)
    }

    // Read All (GET /technicals)
    @GetMapping
    fun getAllTechnical(): ResponseEntity<List<TechnicalResponse>> {
        val response = technicalService.findAllTechnical();
       return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    // Read by Telephone (GET /technicals/telephone)
    @GetMapping("/telephone")
    fun getByTelephoneTechnical(@RequestParam(name = "telephone")telephone: String): ResponseEntity<TechnicalResponse> {
        val response = technicalService.findOneByTelephoneTechnical(telephone)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    // Read by name (GET /technicals/name)
    @GetMapping("/names")
    fun getByNameTechnical(@RequestParam(name = "name")name: String): ResponseEntity<List<TechnicalResponse>> {
        val responses = technicalService.findByNameTechnical(name)
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    // Read by ID (GET /technicals/{id})
    @GetMapping("/{id}")
    fun getByIdTechnical(@PathVariable id: Long): ResponseEntity<TechnicalResponse> {
        val response = technicalService.findByIdTechnical(id)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    // Update (PUT /technicals/{id})
    @PutMapping("/{id}")
    fun updateTechnical(@PathVariable id: Long, @RequestBody @Valid request: TechnicalRequest): ResponseEntity<TechnicalResponse> {
        val response = technicalService.updateTechnical(id, request)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

    // Delete (DELETE /technicals/{id})
    @DeleteMapping("/{id}")
    fun deleteTechnical(@PathVariable id: Long): ResponseEntity<Void> {
        technicalService.deleteTechnical(id)
        return ResponseEntity.noContent().build()
    }
}