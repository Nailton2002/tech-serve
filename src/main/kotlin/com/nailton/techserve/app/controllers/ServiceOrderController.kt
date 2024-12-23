package com.nailton.techserve.app.controllers

import com.nailton.techserve.app.dto.request.ServiceOrderRequest
import com.nailton.techserve.app.dto.request.TechnicalRequest
import com.nailton.techserve.app.dto.response.ServiceOrderResponse
import com.nailton.techserve.app.dto.response.TechnicalResponse
import com.nailton.techserve.intrastructure.services.ServiceOrderService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/services-orders")
class ServiceOrderController(private val service: ServiceOrderService) {


    // Create (POST /services-orders)
    @PostMapping
    fun createTechnical(@RequestBody @Valid request: ServiceOrderRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<ServiceOrderResponse> {

        val response = service.createServiceOrder(request)
        val uri = uriBuilder.path("/services-orders/${response.id}").build().toUri()
        return ResponseEntity.created(uri).body(response)
    }


    // Read All (GET /services-orders)
    @GetMapping
    fun getAllServiceOrder(): ResponseEntity<List<ServiceOrderResponse>> {
        val response = service.findAllServiceOrder();
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }


    // Read by ID (GET /services-orders/{id})
    @GetMapping("/{id}")
    fun getByIdServiceOrder(@PathVariable id: Long): ResponseEntity<ServiceOrderResponse> {
        val response = service.findByIdServiceOrder(id)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }


    // Update (PUT /services-orders/{id})
    @PutMapping("/{id}")
    fun updateServiceOrder(@PathVariable id: Long, @RequestBody @Valid request: ServiceOrderRequest): ResponseEntity<ServiceOrderResponse> {
        val response = service.updateServiceOrder(id, request)
        return ResponseEntity.status(HttpStatus.OK).body(response)
    }

}