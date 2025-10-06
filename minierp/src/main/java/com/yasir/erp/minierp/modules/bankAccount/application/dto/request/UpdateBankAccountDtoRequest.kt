package com.yasir.erp.minierp.modules.bankAccount.application.dto.request

import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus

data class UpdateBankAccountDtoRequest(
    val bankAccountId: String,
    val bankName: String,
    val accountNumber: String,
    val iban: String,
    val currency: String,
    val bankAccountStatus: BankAccountStatus
)
