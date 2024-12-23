package com.nailton.techserve.app.dto.response

import com.nailton.techserve.domain.entities.Technical
import com.nailton.techserve.intrastructure.exceptions.BusinessRuleException

data class TechnicalResponse(

    val id: Long,
    val name: String,
    val telephone: String

) {

    companion object {
        fun fromEntityToResponse(technical: Technical): TechnicalResponse {
            return TechnicalResponse(
                id = technical.id ?: throw BusinessRuleException("O ID do técnico não pode ser nulo."),
                name = technical.name,
                telephone = technical.telephone
            )
        }
    }
}

