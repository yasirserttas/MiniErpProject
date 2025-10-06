package com.yasir.erp.minierp.modules.customer.domain.model

import com.yasir.erp.minierp.modules.address.domain.model.Address
import com.yasir.erp.minierp.modules.order.domain.model.Order
import com.yasir.erp.minierp.modules.user.domain.model.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class Customer(

    @Id
    val id : UUID = UUID.randomUUID(),
    val name: String,
    val surName : String,
    val companyName: String,
    val phoneNumber: String,
    val email: String,
    val taxNumber: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updateAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @OneToMany( mappedBy = "customer", cascade = [CascadeType.ALL]
        , fetch = FetchType.LAZY, orphanRemoval = true)
    val addresses : Set<Address>?= setOf(),

    @OneToMany(mappedBy = "customer",cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY)
    val orders: Set<Order>? = setOf(),
    val active : Boolean = true


    )
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Customer

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}