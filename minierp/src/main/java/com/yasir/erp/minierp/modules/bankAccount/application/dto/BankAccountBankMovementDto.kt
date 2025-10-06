package com.yasir.erp.minierp.modules.bankAccount.application.dto

import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus

data class BankAccountBankMovementDto(
    val id: String,
    val name: String,
    val iban: String,
    val bankAccountStatus: BankAccountStatus
)
