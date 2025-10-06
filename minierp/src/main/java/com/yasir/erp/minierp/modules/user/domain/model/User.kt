package com.yasir.erp.minierp.modules.user.domain.model

import com.yasir.erp.minierp.modules.address.domain.model.Address
import com.yasir.erp.minierp.modules.user.domain.model.Role
import com.yasir.erp.minierp.modules.customer.domain.model.Customer
import com.yasir.erp.minierp.modules.invoice.domain.model.Invoice
import com.yasir.erp.minierp.modules.order.domain.model.Order
import com.yasir.erp.minierp.modules.product.domain.model.Product
import jakarta.persistence.CascadeType
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "users")
data class User(

    @Id
    @Column(nullable = false, updatable = false)
    val id : UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val surName : String,
    @Column(nullable = false)
    val companyName: String,
    @Column(nullable = false)
    val phoneNumber: String,
    @Column(nullable = false)
    val username: String,


    @Column(nullable = false)
    val email: String,
    @Column(nullable = false, length = 72)
    val password: String,

    @Column(nullable = true, unique = false)
    val taxNumber: String,
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany( mappedBy = "user", cascade = [CascadeType.ALL]
        , fetch = FetchType.LAZY, orphanRemoval = true)
    val addresses : Set<Address>?= setOf(),

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY )
    val orders : Set<Order>? = setOf(),

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    val products: Set<Product> ?= setOf(),

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    val customers: Set<Customer>? = setOf(),

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    val invoices: Set<Invoice>? = setOf(),

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role")
    val roles: Set<Role>,

    val active: Boolean

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}