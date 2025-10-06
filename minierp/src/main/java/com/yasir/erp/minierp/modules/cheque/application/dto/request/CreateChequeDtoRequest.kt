package com.yasir.erp.minierp.modules.cheque.application.dto.request

import java.math.BigDecimal
import java.time.LocalDateTime

data class CreateChequeDtoRequest(
    val chequeNumber: String,
    val amount: BigDecimal,
    val issuer: String,
    val issueDate: LocalDateTime,
    val dueDate: LocalDateTime,
    val bankAccountId: String
)