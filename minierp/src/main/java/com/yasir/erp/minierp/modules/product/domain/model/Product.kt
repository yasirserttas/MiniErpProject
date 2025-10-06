package com.yasir.erp.minierp.modules.product.domain.model

import com.yasir.erp.minierp.modules.user.domain.model.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class Product(

    @Id
    @Column(nullable = false, updatable = false)
    val id : String,

    val publicId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    val name: String,
    val description: String? = null,
    val price: BigDecimal,
    val vatRate: BigDecimal,
    val brand: String,
    val category: String,
    val active: Boolean,
    val imageUrl: String? = null,
    val createAt: LocalDateTime,
    val updateAt: LocalDateTime

)