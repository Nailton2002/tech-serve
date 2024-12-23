package com.nailton.techserve.domain.entities

import com.fasterxml.jackson.annotation.JsonFormat
import com.nailton.techserve.app.dto.request.ServiceOrderRequest
import com.nailton.techserve.app.dto.request.TechnicalRequest
import com.nailton.techserve.domain.enums.Status
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "ServiceOrder")
@Table(name = "tb_service_order")
data class ServiceOrder(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val description: String,

    @Enumerated(EnumType.STRING)
    val statusOrder: Status = Status.OPEN,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy'T'HH:mm:ss'Z'", timezone = "GMT")
    val openingDate: LocalDateTime = LocalDateTime.now(),

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "GMT")
    val closingDate: String,

    @ManyToOne(fetch = FetchType.LAZY) // Define o relacionamento Many-to-One
    @JoinColumn(name = "technical_id", nullable = false) // Define a chave estrangeira no banco
    val technical: Technical

) {


    companion object {
        /**
         * Converte um `ServiceOrderRequest` para uma entidade `ServiceOrder`.
         * Observação: É necessário buscar a entidade `Technical` do banco antes de usar esta função.
         */
        fun toEntity(request: ServiceOrderRequest, technical: Technical): ServiceOrder {
            return ServiceOrder(
                description = request.description,
                closingDate = request.closingDate,
                technical = technical // Entidade técnica já carregada
            )
        }
    }


    fun toUpdateEntity(upRequest: ServiceOrderRequest): ServiceOrder {
        return this.copy(
            description = upRequest.description,
            closingDate = upRequest.closingDate
        )
    }


    fun fromRequestToEntity(request: ServiceOrderRequest, technicalRequest: TechnicalRequest): ServiceOrder {
        val technical = Technical(
            name = technicalRequest.name,
            telephone = technicalRequest.telephone
        )
        return ServiceOrder(
            description = request.description,
            closingDate = request.closingDate,
            technical = technical
        )
    }
}