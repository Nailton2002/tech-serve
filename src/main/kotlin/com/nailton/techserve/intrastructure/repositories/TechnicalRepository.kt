package com.nailton.techserve.intrastructure.repositories

import com.nailton.techserve.domain.entities.Technical
import org.springframework.data.jpa.repository.JpaRepository

interface TechnicalRepository : JpaRepository<Technical, Long> {

    // Método para verificar se já existe um técnico com o mesmo nome e telefone
    fun findByNameAndTelephone(name: String, telephone: String): Technical?

}