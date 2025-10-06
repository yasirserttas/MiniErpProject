package com.yasir.erp.minierp.modules.promissoryNote.domain.model

import com.yasir.erp.minierp.common.generator.UlidGenerator
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class PromissoryNote(
    @Id
    val id: String = UlidGenerator.generate(),

    val noteNumber: String,
    val amount: BigDecimal,
    val debtor: String,
    val issueDate: LocalDateTime,
    val dueDate: LocalDateTime,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime? = null,
    val active: Boolean = true,

    @Enumerated(value = EnumType.STRING)
    val status: PromissoryNoteStatus = PromissoryNoteStatus.OPEN,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bank_account_id")
    val bankAccount: BankAccount
)

{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PromissoryNote

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}