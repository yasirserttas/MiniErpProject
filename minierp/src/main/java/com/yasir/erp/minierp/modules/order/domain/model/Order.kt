package com.yasir.erp.minierp.modules.order.domain.model

import com.yasir.erp.minierp.modules.address.domain.model.Address
import com.yasir.erp.minierp.modules.orderItem.domain.model.OrderItem
import com.yasir.erp.minierp.modules.user.domain.model.User
import com.yasir.erp.minierp.common.generator.UlidGenerator
import com.yasir.erp.minierp.modules.customer.domain.model.Customer
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Order(

    @Id
    val id : String = UlidGenerator.generate(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    val customer: Customer,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    val user: User,

    @OneToMany( mappedBy = "order", cascade = [CascadeType.ALL] , fetch = FetchType.LAZY)
    val orderItems : Set<OrderItem> = setOf(),

    @Enumerated(value = EnumType.STRING)
    val orderStatus: OrderStatus,

    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "shipping_address_id")
    val shippingAddress: Address,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "billing_address_id")
    val billingAddress: Address,

    val orderDate: LocalDateTime = LocalDateTime.now(),
    val totalAmount: BigDecimal = BigDecimal.ZERO,
    val note: String? = null,
    val active: Boolean

    )
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Order

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}