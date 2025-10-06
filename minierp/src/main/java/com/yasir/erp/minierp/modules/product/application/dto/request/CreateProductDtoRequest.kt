package com.yasir.erp.minierp.modules.product.application.dto.request

import java.math.BigDecimal
import java.util.UUID

data class CreateProductDtoRequest(
    val userId: UUID,
    val name: String,
    val description: String? = null,
    val price: BigDecimal,
    val vatRate: BigDecimal,
    val brand: String,
    val category: String,
    val imageUrl: String? = null,
    val quantity: Int
)
