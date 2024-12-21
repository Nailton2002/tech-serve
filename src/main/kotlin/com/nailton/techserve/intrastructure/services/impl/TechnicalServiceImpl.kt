package com.nailton.techserve.intrastructure.services.impl

import com.nailton.techserve.app.dto.request.TechnicalRequest
import com.nailton.techserve.app.dto.response.TechnicalResponse
import com.nailton.techserve.domain.entities.Technical
import com.nailton.techserve.intrastructure.exceptions.BusinessRuleException
import com.nailton.techserve.intrastructure.exceptions.DataIntegrityException
import com.nailton.techserve.intrastructure.exceptions.DatabaseException
import com.nailton.techserve.intrastructure.repositories.TechnicalRepository
import com.nailton.techserve.intrastructure.services.TechnicalService
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TechnicalServiceImpl(private val repository: TechnicalRepository) : TechnicalService {

    override fun createTechnical(request: TechnicalRequest): TechnicalResponse {

        // Validação de regra de negócio: não permitir duplicidade de nome e telefone
        val existingTechnical = repository.findByNameAndTelephone(request.name, request.telephone)

        if (existingTechnical != null) {
            // Lança exceção se houver duplicidade
            throw BusinessRuleException("Já existe um técnico com o mesmo nome e telefone")
        }
        try {
            // Converte o DTO para entidade e salva
            val technicalEntity = Technical.fromRequestToEntity(request)

            // Salva a entidade no banco de dados
            val savedTechnical = repository.save(technicalEntity)

            // Converte a entidade salva em DTO de resposta
            return TechnicalResponse.fromEntityToResponse(savedTechnical)
        } catch (ex: Exception) {
            throw DatabaseException("Erro ao salvar técnico no banco de dados", ex)
        }
    }

    override fun findAllTechnical(): List<TechnicalResponse> {

        try {
            // Busca todos os técnicos e os converte para a lista de TechnicalResponse
            val technicals = repository.findAll()

            // Verifica se a lista está vazia
            if (technicals.isEmpty()) {
                throw BusinessRuleException("Nenhum técnico encontrado no sistema")
            }
            return technicals.map { TechnicalResponse.fromEntityToResponse(it) }.toList()

        } catch (ex: Exception) {
            // Trata possíveis problemas de banco de dados ou outros erros inesperados
            throw DatabaseException("Erro ao buscar técnicos no banco de dados", ex)
        }
    }

    override fun findTechnicalByName(name: String): List<TechnicalResponse> {
        val technicalList = repository.findByName(name)
        if (technicalList.isEmpty()){
            throw BusinessRuleException("Nenhum técnico econtrado com o nome: $name")
        }
        return technicalList.stream().map { TechnicalResponse.fromEntityToResponse(it) }.toList()
    }


    override fun findByIdTechnical(id: Long): TechnicalResponse {

        // Validação para ID negativo ou menor que zero será inválido
        if (id <= 0) throw BusinessRuleException("O ID = $id, deve ser maior que zero.")

        try {
            // Busca o técnico pelo ID, lançando exceção personalizada se não encontrado
            val technical = repository.findById(id).orElseThrow {
                BusinessRuleException("Técnico não encontrado para o ID: $id")
            }
            // Converte a entidade encontrada para o DTO de resposta
            return TechnicalResponse.fromEntityToResponse(technical)

        } catch (ex: DataIntegrityViolationException) {
            throw DataIntegrityException("Erro de integridade ao buscar o técnico: ${ex.message}")
        } catch (ex: Exception) {
            throw DatabaseException("Erro ao buscar técnico no banco de dados", ex)
        }
    }


    override fun updateTechnical(id: Long, request: TechnicalRequest): TechnicalResponse {

        // Validação para ID negativo ou menor que zero será inválido
        if (id <= 0) throw BusinessRuleException("O ID = $id, deve ser maior que zero.")

        try {
            // Busca a entidade existente
            val existingTechnical = repository.findById(id).orElseThrow {
                throw BusinessRuleException("Técnico não encontrado para o ID: $id")
            }
            // Cria uma nova instância com os valores atualizados
            val updatedTechnical = existingTechnical.toUpdatedEntity(request)

            // Salva no banco
            val savedTechnical = repository.save(updatedTechnical)

            //E retorna como resposta
            return TechnicalResponse.fromEntityToResponse(savedTechnical)

        } catch (ex: DataIntegrityViolationException) {
            throw DataIntegrityException("Erro de integridade ao atualizar o técnico: ${ex.message}")
        } catch (ex: Exception) {
            throw DatabaseException("Erro ao atualizar técnico no banco de dados", ex)
        }
    }


    override fun deleteTechnical(id: Long) {

        // Validação para ID negativo ou menor que zero será inválido
        if (id <= 0) throw BusinessRuleException("O ID = $id, deve ser maior que zero.")

        try {
            val technical = repository.findById(id).orElseThrow {
                throw BusinessRuleException("Técnico não encontrado para o ID: $id")
            }
            repository.delete(technical)

        } catch (ex: DataIntegrityViolationException) {
            throw DataIntegrityException("Não é possível excluir o técnico pois ele possui ordens de serviço associadas")
        } catch (ex: Exception) {
            throw DatabaseException("Erro ao excluir técnico do banco de dados", ex)
        }

    }
}