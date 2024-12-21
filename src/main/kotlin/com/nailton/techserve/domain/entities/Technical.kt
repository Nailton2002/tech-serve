package com.nailton.techserve.domain.entities

import com.nailton.techserve.app.dto.request.TechnicalRequest
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity(name = "Technical")
@Table(name = "tb_technical")
data class Technical(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null, // ID gerado pelo banco, imutável após criação

    @field:NotBlank(message = "Nome não pode ser vazio")
    val name: String,

    @field:NotBlank(message = "Telefone não pode ser vazio")
    val telephone: String

) {
    companion object {
        /**
         * Converte um DTO de request em uma entidade `Technical`.
         */
        fun fromRequestToEntity(request: TechnicalRequest): Technical {
            return Technical(
                name = request.name,
                telephone = request.telephone
            )
        }
    }

    /**
     * Retorna uma nova instância de `Technical` com os valores atualizados.
     * Em vez de alterar a entidade diretamente, ele retorna uma nova instância da entidade com os valores atualizados.
     */
    fun toUpdatedEntity(upRequest: TechnicalRequest): Technical {
        return this.copy(
            name = upRequest.name ?: this.name, // Mantém o nome atual caso o novo valor seja nulo
            telephone = upRequest.telephone ?: this.telephone // Mantém o telefone atual caso o novo valor seja nulo
        )
    }
}
