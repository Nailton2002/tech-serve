package com.nailton.techserve.domain.entities

import com.nailton.techserve.app.dto.request.TechnicalRequest
import jakarta.persistence.*

@Entity(name = "Technical")
@Table(name = "tb_technical")
data class Technical(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val name: String,

    val telephone: String

) {
    companion object {

        fun fromRequestToEntity(request: TechnicalRequest): Technical {
            return Technical(
                name = request.name,
                telephone = request.telephone
            )
        }
    }

}
