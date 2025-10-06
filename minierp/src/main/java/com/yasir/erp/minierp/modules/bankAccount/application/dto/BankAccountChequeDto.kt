package com.yasir.erp.minierp.modules.bankAccount.application.dto

import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus

data class BankAccountChequeDto(
    val id: String,
    val bankName: String,
    val iban: String,
    val bankAccountStatus: BankAccountStatus
)
