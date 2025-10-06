package com.yasir.erp.minierp.modules.invoice.application.dto.request

import java.math.BigDecimal
import java.time.LocalDateTime

data class UpdateInvoiceDtoRequest(
    val id: String,
    val invoiceNumber: String,
    val invoiceDate: LocalDateTime,
    val totalAmount: BigDecimal,
    val taxAmount: BigDecimal,
    val finalAmount: BigDecimal,

)
