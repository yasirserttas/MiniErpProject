package com.yasir.erp.minierp.modules.purchaseOrder.domain.model

import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier
import com.yasir.erp.minierp.common.generator.UlidGenerator
import com.yasir.erp.minierp.modules.purchaseItem.domain.model.PurchaseItem
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class PurchaseOrder(
    @Id
    @Column(nullable = false, updatable = false)
    val id: String = UlidGenerator.generate(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false , name = "supplier_id")
    val supplier: Supplier,

    @OneToMany(mappedBy = "purchaseOrder", cascade = [CascadeType.ALL], orphanRemoval = true)
    val purchaseItems: Set<PurchaseItem> = setOf(),

    val totalAmount: BigDecimal = BigDecimal.ZERO,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: PurchaseOrderStatus = PurchaseOrderStatus.PENDING,

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val active: Boolean = true,

    ){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PurchaseOrder

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}