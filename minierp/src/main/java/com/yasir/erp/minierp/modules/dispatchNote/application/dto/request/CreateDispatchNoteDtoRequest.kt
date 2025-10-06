package com.yasir.erp.minierp.dto.dispatchNote.request

import java.time.LocalDateTime
import java.util.UUID

data class CreateDispatchNoteDtoRequest(
    val userId: UUID,
    val customerId: UUID,
    val orderId: String,
    val transporterName: String? = null,
    val transporterPlate: String? = null,
    val dispatchDate: LocalDateTime,
    val estimatedArrival: LocalDateTime? = null,
    val dispatchNoteNumber: String,
    val deliveredBy: String?,
    val receivedBy: String?
)
