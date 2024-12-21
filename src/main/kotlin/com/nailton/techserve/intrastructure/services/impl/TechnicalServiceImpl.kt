package com.nailton.techserve.intrastructure.services.impl

import com.nailton.techserve.app.dto.request.TechnicalRequest
import com.nailton.techserve.app.dto.response.TechnicalResponse
import com.nailton.techserve.domain.entities.Technical
import com.nailton.techserve.intrastructure.repositories.TechnicalRepository
import com.nailton.techserve.intrastructure.services.TechnicalService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TechnicalServiceImpl(private val repository: TechnicalRepository) : TechnicalService {

    override fun createTechnical(request: TechnicalRequest): TechnicalResponse {

        // Verifica se já existe um técnico com o mesmo nome e telefone
        val existingTechnical = repository.findByNameAndTelephone(request.name, request.telephone)

        if (existingTechnical != null) {
            // Lança exceção se houver duplicidade
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um técnico com o mesmo nome e telefone")
        }

        // Converte o request em entidade
        val technicalEntity = Technical.fromRequestToEntity(request)

        // Salva a entidade no banco de dados
        val savedTechnical = repository.save(technicalEntity)

        // Converte a entidade salva em DTO de resposta
        return TechnicalResponse.fromEntityToResponse(savedTechnical)
    }
}
