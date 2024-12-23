package com.nailton.techserve.intrastructure.services

import com.nailton.techserve.app.dto.request.ServiceOrderRequest
import com.nailton.techserve.app.dto.response.ServiceOrderResponse
import org.springframework.transaction.annotation.Transactional

interface ServiceOrderService {

    @Transactional
    fun createServiceOrder(request: ServiceOrderRequest): ServiceOrderResponse


    @Transactional(readOnly = true)
    fun findAllServiceOrder(): List<ServiceOrderResponse>


    @Transactional(readOnly = true)
    fun findByIdServiceOrder(id: Long): ServiceOrderResponse

    @Transactional
    fun updateServiceOrder(id: Long, request: ServiceOrderRequest): ServiceOrderResponse

}
