package com.yasir.erp.minierp.modules.stockMovement.domain.model

import com.yasir.erp.minierp.common.generator.UlidGenerator
import com.yasir.erp.minierp.modules.product.domain.model.Product
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
data class StockMovement(
    @Id
    val id: String = UlidGenerator.generate(),

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "product_id")
    val product: Product,

    val quantity: Int,
    val movementType: StockMovementType,
    val description: String? = null,
    val date: LocalDateTime = LocalDateTime.now()
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StockMovement

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}