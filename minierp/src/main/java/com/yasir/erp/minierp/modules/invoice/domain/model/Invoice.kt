package com.yasir.erp.minierp.modules.invoice.domain.model

import com.yasir.erp.minierp.modules.order.domain.model.Order
import com.yasir.erp.minierp.modules.user.domain.model.User
import com.yasir.erp.minierp.common.generator.ShortIdGenerator
import com.yasir.erp.minierp.common.generator.UlidGenerator
import com.yasir.erp.minierp.modules.customer.domain.model.Customer
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class Invoice(
    @Id
    @Column(nullable = false, updatable = false)
    val id: String = UlidGenerator.generate(),

    @Column(nullable = false, unique = true)
    val invoiceNumber: String = ShortIdGenerator.generate(10),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "customer_id" ,nullable = false)
    val customer: Customer,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    val order: Order,

    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime,
    val invoiceDate: LocalDateTime = LocalDateTime.now(),
    val totalAmount: BigDecimal,
    val taxAmount: BigDecimal,
    val finalAmount: BigDecimal,
    val issuedBy: String,
    val receivedBy: String,
    val active: Boolean = true,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Invoice

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}