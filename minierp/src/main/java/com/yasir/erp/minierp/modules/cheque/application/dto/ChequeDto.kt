package com.yasir.erp.minierp.modules.cheque.application.dto

import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountChequeDto
import com.yasir.erp.minierp.modules.cheque.domain.model.ChequeStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class ChequeDto(
    val id: String,
    val chequeNumber: String,
    val amount: BigDecimal,
    val issuer: String,
    val issueDate: LocalDateTime,
    val dueDate: LocalDateTime,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null,
    val active: Boolean,
    val status: ChequeStatus,
    val bankAccount: BankAccountChequeDto
)
