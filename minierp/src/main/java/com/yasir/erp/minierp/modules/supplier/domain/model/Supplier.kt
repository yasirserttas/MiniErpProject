package com.yasir.erp.minierp.modules.supplier.domain.model

import com.yasir.erp.minierp.modules.address.domain.model.Address
import com.yasir.erp.minierp.common.generator.UlidGenerator
import com.yasir.erp.minierp.modules.purchaseOrder.domain.model.PurchaseOrder
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.time.LocalDateTime

@Entity
data class Supplier(
    @Id
    @Column(nullable = false, updatable = false)
    val id: String = UlidGenerator.generate(),

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val taxNumber: String,

    @Column(nullable = false)
    val phone: String,

    @Column(nullable = false)
    val email: String,


    @OneToMany(mappedBy = "supplier", cascade = [CascadeType.ALL]
        , fetch = FetchType.LAZY)
    val purchaseOrders: Set<PurchaseOrder>?,

    @OneToMany( mappedBy = "supplier", cascade = [CascadeType.ALL]
        , fetch = FetchType.LAZY, orphanRemoval = true)
    val addresses : Set<Address>?= setOf(),

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val updateAt: LocalDateTime,

    @Column(nullable = false)
    val active: Boolean

    ){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Supplier

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}