package com.yasir.erp.minierp.modules.orderItem.domain.model

import com.yasir.erp.minierp.modules.product.domain.model.Product
import com.yasir.erp.minierp.common.generator.UlidGenerator
import com.yasir.erp.minierp.modules.order.domain.model.Order
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import java.math.BigDecimal

@Entity
data class OrderItem(
    @Id
    val id :String = UlidGenerator.generate(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    val order: Order,

    val quantity : Int,
    val unitPrice : BigDecimal,
    val taxRateApplied : BigDecimal,
    val taxAmount : BigDecimal,
    val totalPriceWithTax : BigDecimal
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderItem

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}