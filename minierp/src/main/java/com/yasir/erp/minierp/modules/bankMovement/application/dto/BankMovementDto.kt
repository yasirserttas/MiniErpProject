package com.yasir.erp.minierp.modules.bankMovement.application.dto

import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountBankMovementDto
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementStatus
import com.yasir.erp.minierp.modules.bankMovement.domain.model.BankMovementType
import java.math.BigDecimal
import java.time.LocalDateTime

data class BankMovementDto(

    val id: String,
    val amount: BigDecimal,
    val type: BankMovementType,
    val bankMovementStatus: BankMovementStatus,
    val bankAccount: BankAccountBankMovementDto,
    val description: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val date: LocalDateTime
)