package com.yasir.erp.minierp.modules.bankMovement.application.dto.request

import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementStatus
import java.math.BigDecimal

data class UpdateBankMovementDtoRequest(
    val id: String,
    val amount: BigDecimal,
    val bankMovementStatus: BankMovementStatus,
    val bankAccountId: String,
    val description: String?,
)
