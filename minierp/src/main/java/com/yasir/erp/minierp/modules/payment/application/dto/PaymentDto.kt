package com.yasir.erp.minierp.modules.payment.application.dto


import com.yasir.erp.minierp.modules.invoice.application.dto.InvoicePaymentDto
import com.yasir.erp.minierp.modules.payment.domain.model.PaymentMethod
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentDto(
    val id: String,
    val invoice: InvoicePaymentDto,
    val amount: BigDecimal,
    val method: PaymentMethod,
    val paidAt: LocalDateTime,
    val issueDate: LocalDateTime?,
    val dueDate: LocalDateTime?,
    val note: String?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime
)
