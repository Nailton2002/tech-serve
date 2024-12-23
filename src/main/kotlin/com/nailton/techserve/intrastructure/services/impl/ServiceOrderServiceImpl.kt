package com.nailton.techserve.intrastructure.services.impl

import com.nailton.techserve.app.dto.request.ServiceOrderRequest
import com.nailton.techserve.app.dto.response.ServiceOrderResponse
import com.nailton.techserve.domain.entities.ServiceOrder
import com.nailton.techserve.intrastructure.exceptions.BusinessRuleException
import com.nailton.techserve.intrastructure.exceptions.DatabaseException
import com.nailton.techserve.intrastructure.repositories.ServiceOrderRepository
import com.nailton.techserve.intrastructure.services.ServiceOrderService
import com.nailton.techserve.intrastructure.services.TechnicalService
import org.springframework.stereotype.Service

@Service
class ServiceOrderServiceImpl(

    private val repository: ServiceOrderRepository,
    private val technicalRepository: TechnicalService

) : ServiceOrderService {

    override fun createServiceOrder(request: ServiceOrderRequest): ServiceOrderResponse {
        try {
            val technical = technicalRepository.findEntityById(request.technicalId)

            val serviceOrder = ServiceOrder.toEntity(request, technical)

            val savedEntity = repository.save(serviceOrder)

            return ServiceOrderResponse.fromEntityToResponse(savedEntity)

        } catch (ex: Exception) {
            throw DatabaseException("Erro ao salvar a ordem de serviço no banco de dados", ex)
        }
    }


    override fun findAllServiceOrder(): List<ServiceOrderResponse> {
        try {
            val serviceOrders = repository.findAll()

            if (serviceOrders.isEmpty()) {
                throw BusinessRuleException("Nenhum OS encontrado no sistema")
            }
            return serviceOrders.map { ServiceOrderResponse.fromEntityToResponse(it) }.toList()

        } catch (ex: Exception) {
            throw DatabaseException("Erro ao buscar OS no banco de dados", ex)
        }
    }

}
