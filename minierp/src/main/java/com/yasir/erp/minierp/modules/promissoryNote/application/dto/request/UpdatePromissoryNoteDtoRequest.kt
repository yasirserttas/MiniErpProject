package com.yasir.erp.minierp.modules.promissoryNote.application.dto.request

import java.math.BigDecimal
import java.time.LocalDateTime

data class UpdatePromissoryNoteDtoRequest(
    val id: String,
    val amount: BigDecimal,
    val debtor: String,
    val dueDate: LocalDateTime,
    val noteNumber: String
)
