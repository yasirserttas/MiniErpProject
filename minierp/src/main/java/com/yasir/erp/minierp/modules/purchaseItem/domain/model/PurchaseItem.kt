package com.yasir.erp.minierp.modules.purchaseItem.domain.model

import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder
import com.yasir.erp.minierp.common.generator.UlidGenerator
import com.yasir.erp.minierp.modules.product.domain.model.Product
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal

@Entity
data class PurchaseItem(

    @Id
    @Column(nullable = false, updatable = false)
    val id: String = UlidGenerator.generate(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    val product: Product,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    val purchaseOrder: PurchaseOrder? = null,

    @Column(nullable = false)
    val quantity: Int,

    @Column(nullable = false)
    val purchasePrice: BigDecimal,

    @Column(nullable = false)
    val taxRate: BigDecimal,

    @Column(nullable = false)
    val taxAmount: BigDecimal,

    @Column(nullable = false)
    val totalCost: BigDecimal


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PurchaseItem

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}