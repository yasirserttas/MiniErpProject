package com.yasir.erp.minierp.modules.promissoryNote.application.dto.request

import java.math.BigDecimal
import java.time.LocalDateTime

data class CreatePromissoryNoteDtoRequest(
    val noteNumber: String,
    val amount: BigDecimal,
    val debtor: String,
    val issueDate: LocalDateTime,
    val dueDate: LocalDateTime,
    val bankAccountId: String
)
