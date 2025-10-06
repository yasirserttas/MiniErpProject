package com.yasir.erp.minierp.modules.invoice.application.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class InvoicePaymentDto(
    val id: String,
    val invoiceNumber: String,
    val invoiceDate: LocalDateTime,
    val finalAmount: BigDecimal,
    val active: Boolean
)
