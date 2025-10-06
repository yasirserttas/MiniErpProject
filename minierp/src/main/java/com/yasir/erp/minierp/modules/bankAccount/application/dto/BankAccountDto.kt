package com.yasir.erp.minierp.modules.bankAccount.application.dto


import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class BankAccountDto(
    val id: String,
    val bankName: String,
    val accountNumber: String,
    val iban: String,
    val currency: String,
    val balance: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt : LocalDateTime?,
    val bankAccountStatus: BankAccountStatus
)
