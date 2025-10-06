package com.yasir.erp.minierp.dto.dispatchNote.request

import java.time.LocalDateTime

data class CreateOrderDispatchedDispatchOrder(

    val transporterName: String? ,
    val transporterPlate: String? ,
    val estimatedArrival: LocalDateTime?,
)
