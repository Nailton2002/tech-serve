package com.nailton.techserve.app.dto.request

import com.fasterxml.jackson.annotation.JsonFormat

data class ServiceOrderRequest(

    val description: String,

    val technicalId: Long,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "GMT")
    val closingDate: String

)
