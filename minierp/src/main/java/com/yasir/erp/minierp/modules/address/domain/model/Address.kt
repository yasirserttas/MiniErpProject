package com.yasir.erp.minierp.modules.address.domain.model

import com.yasir.erp.minierp.common.generator.UlidGenerator
import com.yasir.erp.minierp.modules.customer.domain.model.Customer
import com.yasir.erp.minierp.modules.supplier.domain.model.Supplier
import com.yasir.erp.minierp.modules.user.domain.model.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class Address(

    @Id
    @Column(nullable = false, updatable = false)
    val id: String = UlidGenerator.generate(),

    val street : String,
    val district: String,
    val city : String,
    val country : String,
    val postalCode: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    val customer: Customer?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    val supplier: Supplier?

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}