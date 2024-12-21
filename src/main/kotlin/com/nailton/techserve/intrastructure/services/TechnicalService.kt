package com.nailton.techserve.intrastructure.services

import com.nailton.techserve.app.dto.request.TechnicalRequest
import com.nailton.techserve.app.dto.response.TechnicalResponse

interface  TechnicalService {

    fun createTechnical(request: TechnicalRequest): TechnicalResponse
}