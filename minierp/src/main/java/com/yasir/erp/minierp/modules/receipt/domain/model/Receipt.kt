package com.yasir.erp.minierp.modules.receipt.domain.model

import com.yasir.erp.minierp.common.generator.ShortIdGenerator
import com.yasir.erp.minierp.common.generator.UlidGenerator
import com.yasir.erp.minierp.modules.payment.domain.model.Payment
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
data class Receipt(

    @Id
    val id: String = UlidGenerator.generate(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    val payment: Payment,

    @Column(nullable = false, unique = true)
    val receiptNumber: String = ShortIdGenerator.generate(10),



    val issuedBy: String,
    val receivedBy: String,
    val receiptDate: LocalDateTime,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime,
    val active: Boolean
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Receipt

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}