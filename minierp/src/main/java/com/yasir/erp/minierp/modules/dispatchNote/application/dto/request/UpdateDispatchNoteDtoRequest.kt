package com.yasir.erp.minierp.dto.dispatchNote.request


import com.yasir.erp.minierp.modules.address.application.dto.AddressCustomerDto
import java.time.LocalDateTime

data class UpdateDispatchNoteDtoRequest(
    val dispatchNoteId: String,
    val transporterName: String? = null,
    val transporterPlate: String? = null,
    val estimatedArrival: LocalDateTime? = null,
    val dispatchNoteNumber: String,
    val deliveredBy: String?,
    val receivedBy: String?
)
