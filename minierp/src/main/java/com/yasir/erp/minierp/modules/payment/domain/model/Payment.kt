package com.yasir.erp.minierp.modules.payment.domain.model

import com.yasir.erp.minierp.common.generator.UlidGenerator
import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice
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
data class Payment(

    @Id
    val id: String = UlidGenerator.generate(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "invoice_id", nullable = false)
    val invoice: Invoice,

    val amount: BigDecimal,

    @Enumerated(EnumType.STRING)
    val method: PaymentMethod,

    val paidAt: LocalDateTime = LocalDateTime.now(),
    val issueDate: LocalDateTime? = null,
    val dueDate: LocalDateTime? = null,
    val note: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Payment

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}