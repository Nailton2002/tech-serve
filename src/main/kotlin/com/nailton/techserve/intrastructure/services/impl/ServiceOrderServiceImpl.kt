package com.nailton.techserve.intrastructure.services.impl

import com.nailton.techserve.app.dto.request.ServiceOrderRequest
import com.nailton.techserve.app.dto.response.ServiceOrderResponse
import com.nailton.techserve.domain.entities.ServiceOrder
import com.nailton.techserve.intrastructure.exceptions.DatabaseException
import com.nailton.techserve.intrastructure.exceptions.ResourceNotFoundException
import com.nailton.techserve.intrastructure.repositories.ServiceOrderRepository
import com.nailton.techserve.intrastructure.repositories.TechnicalRepository
import com.nailton.techserve.intrastructure.services.ServiceOrderService
import org.springframework.stereotype.Service

@Service
class ServiceOrderServiceImpl(
    private val repository: ServiceOrderRepository,
    private val technicalRepository: TechnicalRepository
) : ServiceOrderService {

    override fun createServiceOrder(request: ServiceOrderRequest): ServiceOrderResponse {

        try {
            val technical = technicalRepository.findById(request.technicalId).orElseThrow {
                ResourceNotFoundException("Técnico não econtrado")
            }
            val serviceOrder = ServiceOrder.fromRequestToEntity(request, technical)

            val savedEntity = repository.save(serviceOrder)

            return ServiceOrderResponse.fromEntityToResponse(savedEntity)

        } catch (ex: Exception) {
            throw DatabaseException("Erro ao salvar a ordem de serviço no banco de dados", ex)
        }
    }
}