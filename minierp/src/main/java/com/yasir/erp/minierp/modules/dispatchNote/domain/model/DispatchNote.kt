package com.yasir.erp.minierp.modules.dispatchNote.domain.model

import com.yasir.erp.minierp.modules.address.domain.model.Address
import com.yasir.erp.minierp.modules.order.domain.model.Order
import com.yasir.erp.minierp.modules.user.domain.model.User
import com.yasir.erp.minierp.common.generator.UlidGenerator
import com.yasir.erp.minierp.modules.customer.domain.model.Customer
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import java.time.LocalDateTime

@Entity
data class DispatchNote(

    @Id
    @Column(nullable = false, updatable = false)
    val id: String = UlidGenerator.generate(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",nullable = false)
    val customer: Customer,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    val order: Order,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id", nullable = false)
    val deliveryAddress: Address,

    val transporterName: String? = null,
    val transporterPlate: String? = null,
    val dispatchDate: LocalDateTime ?,
    val estimatedArrival: LocalDateTime? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updateAt: LocalDateTime,

    @Column(unique = true, nullable = false)
    val dispatchNoteNumber: String,

    @Enumerated(value = EnumType.STRING)
    val dispatchNoteStatus: DispatchNoteStatus,

    val deliveredBy: String?,
    val receivedBy: String?,
    val active: Boolean

    )
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DispatchNote

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}