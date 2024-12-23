package com.nailton.techserve.app.controllers

import com.nailton.techserve.app.dto.request.ServiceOrderRequest
import com.nailton.techserve.app.dto.response.ServiceOrderResponse
import com.nailton.techserve.intrastructure.services.ServiceOrderService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/services-orders")
class ServiceOrderController(private val serviceOrder: ServiceOrderService) {

    // Create (POST /services-orders)
    @PostMapping
    fun createTechnical(@RequestBody @Valid request: ServiceOrderRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<ServiceOrderResponse> {

        val response = serviceOrder.createServiceOrder(request)
        val uri = uriBuilder.path("/services-orders/${response.id}").build().toUri()
        return ResponseEntity.created(uri).body(response)
    }
}