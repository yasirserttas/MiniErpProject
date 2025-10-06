package com.yasir.erp.minierp.modules.payment.application.dto

import com.yasir.erp.minierp.modules.payment.domain.model.PaymentMethod
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentReceiptDto(
    val id: String,
    val amount: BigDecimal,
    val method: PaymentMethod,
    val paidAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime

)
