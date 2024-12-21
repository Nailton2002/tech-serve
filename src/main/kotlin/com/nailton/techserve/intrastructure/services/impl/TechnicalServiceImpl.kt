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


    override fun findAllTechnical(): List<TechnicalResponse> {
        return repository.findAll().map { TechnicalResponse.fromEntityToResponse(it) }.toList()
    }


    override fun findByIdTechnical(id: Long): TechnicalResponse {
        val technical = repository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Técnico não encontrado")
        }
        return TechnicalResponse.fromEntityToResponse(technical)
    }


    override fun updateTechnical(id: Long, request: TechnicalRequest): TechnicalResponse {
        // Busca a entidade existente
        val existingTechnical = repository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Técnico não encontrado")
        }
        // Cria uma nova instância com os valores atualizados
        val updatedTechnical = existingTechnical.toUpdatedEntity(request)

        // Salva no banco
        val savedTechnical = repository.save(updatedTechnical)

        //E retorna como resposta
        return TechnicalResponse.fromEntityToResponse(savedTechnical)
    }


    override fun deleteTechnical(id: Long) {
        val technical = repository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Técnico não encontrado")
        }
        repository.delete(technical)
    }

}
