package com.nailton.techserve.intrastructure.repositories

import com.nailton.techserve.domain.entities.Technical
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface TechnicalRepository : JpaRepository<Technical, Long> {

    // Método para verificar se já existe um técnico com o mesmo nome e telefone
    fun findByNameAndTelephone(name: String, telephone: String): Technical?

//    @Query("SELECT t FROM Technical t WHERE UPPER(t.name) LIKE UPPER(CONCAT('%', :name, '%'))")
//    fun findByName(@Param("name") name: String): List<Technical>

    @Query(
        """
    SELECT t 
    FROM Technical t 
    WHERE (:name IS NULL OR UPPER(t.name) LIKE UPPER(CONCAT('%', :name, '%')))
"""
    )
    fun findByName(@Param("name") name: String): List<Technical>


    /*  Observação:
     *  Se desejar retornar apenas um técnico e tiver certeza de que a busca resultará em um único resultado
     *   (o que pode ser perigoso em telephone genéricos), use Optional<Technical> como retorno:
     */
    @Query("SELECT t FROM Technical t WHERE t.telephone = :telephone")
    fun findOneByTelephone(@Param("telephone") telephone: String): Optional<Technical>

}