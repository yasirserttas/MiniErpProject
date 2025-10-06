package com.yasir.erp.minierp.modules.bankMovement.application.dto.request

import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementType
import java.math.BigDecimal

data class CreateBankMovementDtoRequest(
    val bankAccountId: String,
    val amount: BigDecimal,
    val type: BankMovementType,
    val description: String?

)
