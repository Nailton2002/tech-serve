package com.nailton.techserve.intrastructure.repositories

import com.nailton.techserve.domain.entities.ServiceOrder
import org.springframework.data.jpa.repository.JpaRepository

interface ServiceOrderRepository: JpaRepository<ServiceOrder, Long> {

}
