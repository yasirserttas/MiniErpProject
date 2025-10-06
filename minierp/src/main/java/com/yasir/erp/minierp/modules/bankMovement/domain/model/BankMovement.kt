package com.yasir.erp.minierp.modules.bankMovement.domain.model

import com.yasir.erp.minierp.common.generator.UlidGenerator
import com.yasir.erp.minierp.modules.bankAccount.domain.model.BankAccount
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class BankMovement(
    @Id
    val id: String = UlidGenerator.generate(),

    @Column(nullable = false)
    val amount: BigDecimal,

    @Enumerated(value = EnumType.STRING)
    val type: BankMovementType,

    @Enumerated(value = EnumType.STRING)
    val bankMovementStatus: BankMovementStatus = BankMovementStatus.ACTIVE,

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    val bankAccount: BankAccount,

    val description: String? = null,
    val createdAt : LocalDateTime = LocalDateTime.now(),
    val updatedAt : LocalDateTime?,
    val date: LocalDateTime = LocalDateTime.now()
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BankMovement

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}