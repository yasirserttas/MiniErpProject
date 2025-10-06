package com.yasir.erp.minierp.modules.bankAccount.domain.model

import com.yasir.erp.minierp.common.generator.UlidGenerator
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class BankAccount(
    @Id
    val id: String = UlidGenerator.generate(),

    val bankName: String,

    @Column(unique = true, nullable = false)
    val accountNumber: String,
    @Column(unique = true, nullable = false)
    val iban: String,

    val currency: String = "TRY",
    val balance: BigDecimal = BigDecimal.ZERO,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt : LocalDateTime?,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val status: BankAccountStatus



){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BankAccount) return false
        return id == other.id
    }


    override fun hashCode(): Int {
        return id.hashCode()
    }
}