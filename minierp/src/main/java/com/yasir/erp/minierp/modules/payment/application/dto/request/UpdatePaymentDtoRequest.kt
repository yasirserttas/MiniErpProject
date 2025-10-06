package com.yasir.erp.minierp.modules.payment.application.dto.request

import com.yasir.erp.minierp.modules.payment.domain.model.PaymentMethod
import java.math.BigDecimal
import java.time.LocalDateTime

data class UpdatePaymentDtoRequest(
    val id: String,
    val amount: BigDecimal,
    val method: PaymentMethod,
    val paidAt: LocalDateTime,
    val issueDate: LocalDateTime? = null,
    val dueDate: LocalDateTime? = null,
    val note: String? = null
)
