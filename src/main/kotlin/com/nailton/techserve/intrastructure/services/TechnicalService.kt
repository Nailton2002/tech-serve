package com.nailton.techserve.intrastructure.services

import com.nailton.techserve.app.dto.request.TechnicalRequest
import com.nailton.techserve.app.dto.response.TechnicalResponse

interface  TechnicalService {

    fun createTechnical(request: TechnicalRequest): TechnicalResponse

    fun findAllTechnical(): List<TechnicalResponse>

    fun findByIdTechnical(id: Long): TechnicalResponse

    fun updateTechnical(id: Long, request: TechnicalRequest): TechnicalResponse

    fun deleteTechnical(id: Long) {

    }
}