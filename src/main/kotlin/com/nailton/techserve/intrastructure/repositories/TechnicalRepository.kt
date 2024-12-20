package com.nailton.techserve.intrastructure.repositories

import com.nailton.techserve.domain.entities.Technical
import org.springframework.data.jpa.repository.JpaRepository

interface TechnicalRepository: JpaRepository<Technical, Long> {
}