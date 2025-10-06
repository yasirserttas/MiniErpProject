package com.yasir.erp.minierp.modules.invoice.application.dto.request

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class CreateInvoiceDtoRequest(
    val invoiceNumber: String?,
    val userId: UUID,
    val customerId: UUID,
    val orderId: String,
    val invoiceDate: LocalDateTime,

)
