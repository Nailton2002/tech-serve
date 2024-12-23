package com.nailton.techserve.app.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.nailton.techserve.domain.entities.ServiceOrder
import com.nailton.techserve.domain.enums.Status
import com.nailton.techserve.intrastructure.exceptions.BusinessRuleException
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDateTime

data class ServiceOrderResponse(

    val id: Long,

    val description: String,

    @Enumerated(EnumType.STRING)
    val statusOrder: Status,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy'T'HH:mm:ss'Z'", timezone = "GMT")
    val openingDate: LocalDateTime = LocalDateTime.now(),

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "GMT")
    val closingDate: String?,

    val technical: TechnicalResponse
) {
    companion object {
        fun fromEntityToResponse(serviceOrder: ServiceOrder): ServiceOrderResponse {
            return ServiceOrderResponse(
                id = serviceOrder.id ?: throw BusinessRuleException("O ID do serviço não pode ser nulo."),
                description = serviceOrder.description,
                statusOrder = serviceOrder.statusOrder,
                openingDate = serviceOrder.openingDate,
                closingDate = serviceOrder.closingDate,
                technical = TechnicalResponse.fromEntityToResponse(serviceOrder.technical) // Converte o técnico associado
            )
        }
    }
}
// Extensão para converter entidade em DTO
fun ServiceOrder.toResponse() = ServiceOrderResponse(
    id = this.id!!,
    description = this.description,
    openingDate = this.openingDate,
    closingDate = this.closingDate,
    statusOrder = this.statusOrder,
    technical = TechnicalResponse(this.technical.id!!, this.technical.name, this.technical.telephone),
)
