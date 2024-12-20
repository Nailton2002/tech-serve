package com.nailton.techserve.app.dto.response

import com.nailton.techserve.domain.entities.Technical

data class TechnicalResponse(

    val id: Long,
    val name: String,
    val telephone: String

) {

    companion object {
        fun fromEntityToResponse(technical: Technical): TechnicalResponse {
            return TechnicalResponse(
                id = technical.id ?: throw IllegalArgumentException("ID cannot be null"),
                name = technical.name,
                telephone = technical.telephone
            )
        }
    }
}

