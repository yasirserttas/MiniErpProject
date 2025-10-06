package com.yasir.erp.minierp.modules.bankAccount.application.dto.request

import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccountStatus
import java.math.BigDecimal

data class CreateBankAccountDtoRequest(
    val bankName: String,
    val accountNumber: String,
    val iban: String,
    val currency: String,
    val currentBalance: BigDecimal,
    val bankAccountStatus: BankAccountStatus
)
