package com.yasir.erp.minierp.modules.stock.domain.model

import com.yasir.erp.minierp.common.generator.UlidGenerator
import com.yasir.erp.minierp.modules.product.domain.model.Product
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.LocalDateTime

@Entity
data class Stock(
    @Id
    val id: String = UlidGenerator.generate(),

    @OneToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(nullable = false)
    val product: Product,

    val quantity: Int? = 0,

    val lastUpdated: LocalDateTime = LocalDateTime.now()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Stock

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}