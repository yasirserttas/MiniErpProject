package com.yasir.erp.minierp.modules.product.application.dto

import com.yasir.erp.minierp.modules.user.application.dto.UserProductDto
import java.math.BigDecimal
import java.time.LocalDateTime

data class ProductDto(
    val id: String,
    val publicId: String,
    val user: UserProductDto,
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
