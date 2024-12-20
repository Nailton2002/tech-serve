package com.nailton.techserve.intrastructure.services

import com.nailton.techserve.app.dto.request.TechnicalRequest
import com.nailton.techserve.app.dto.response.TechnicalResponse
import com.nailton.techserve.domain.entities.Technical
import com.nailton.techserve.intrastructure.repositories.TechnicalRepository
import org.springframework.stereotype.Service

@Service
class TechnicalService(private val repository: TechnicalRepository) {

    fun createTechnical(request: TechnicalRequest): TechnicalResponse {
        // Converte o request em uma entidade
        val tec = Technical.fromRequestToEntity(request)

        // Salva a entidade no banco de dados
        val savedTechnical = repository.save(tec)

        // Converte a entidade salva em um TechnicalResponse
        return TechnicalResponse.fromEntityToResponse(savedTechnical)
    }
}
