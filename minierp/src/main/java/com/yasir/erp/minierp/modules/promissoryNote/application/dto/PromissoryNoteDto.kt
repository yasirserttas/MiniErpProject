package com.yasir.erp.minierp.modules.promissoryNote.application.dto


import com.yasir.erp.minierp.modules.bankAccount.application.dto.BankAccountPromissoryDto
import com.yasir.erp.minierp.modules.promissoryNote.domain.model.PromissoryNoteStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class PromissoryNoteDto(
    val id: String,
    val noteNumber: String,
    val amount: BigDecimal,
    val debtor: String,
    val issueDate: LocalDateTime,
    val dueDate: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
    val active: Boolean,
    val status: PromissoryNoteStatus,
    val bankAccount: BankAccountPromissoryDto
)
