package com.nailton.techserve.intrastructure.services

import com.nailton.techserve.app.dto.request.TechnicalRequest
import com.nailton.techserve.app.dto.response.TechnicalResponse
import com.nailton.techserve.domain.entities.Technical
import com.nailton.techserve.intrastructure.repositories.TechnicalRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TechnicalService(private val repository: TechnicalRepository) {

    fun createTechnical(request: TechnicalRequest): TechnicalResponse {

        // Verifica se já existe um técnico com o mesmo nome e telefone
        val existingTechnical = repository.findByNameAndTelephone(request.name, request.telephone)

        if (existingTechnical != null) {
            // Se já existir, lança uma exceção com status 400 (Bad Request)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um técnico com o mesmo nome e telefone")
        }
        // Converte o request em uma entidade
        val tec = Technical.fromRequestToEntity(request)

        // Salva a entidade no banco de dados
        val savedTechnical = repository.save(tec)

        // Converte a entidade salva em um TechnicalResponse
        return TechnicalResponse.fromEntityToResponse(savedTechnical)
    }

}
