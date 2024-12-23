package com.nailton.techserve.intrastructure.services

import com.nailton.techserve.app.dto.request.TechnicalRequest
import com.nailton.techserve.app.dto.response.TechnicalResponse
import org.springframework.transaction.annotation.Transactional

interface TechnicalService {

    @Transactional
    fun createTechnical(request: TechnicalRequest): TechnicalResponse

    @Transactional(readOnly = true)
    fun findAllTechnical(): List<TechnicalResponse>

    @Transactional(readOnly = true)
    fun findByNameTechnical(name: String): List<TechnicalResponse>

    @Transactional(readOnly = true)
    fun findOneByTelephoneTechnical(telephone: String): TechnicalResponse

    @Transactional(readOnly = true)
    fun findByIdTechnical(id: Long): TechnicalResponse

    @Transactional
    fun updateTechnical(id: Long, request: TechnicalRequest): TechnicalResponse

    @Transactional
    fun deleteTechnical(id: Long)
}

/*
 *   Use readOnly = true para métodos de leitura.
 *   Para métodos que apenas fazem consultas (como findAll e findById), você pode usar @Transactional(readOnly = true)
 *  para otimizar o desempenho, indicando que nenhuma operação de escrita será realizada.
*/
