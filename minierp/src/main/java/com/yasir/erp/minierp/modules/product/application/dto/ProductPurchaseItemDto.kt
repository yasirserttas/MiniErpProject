package com.yasir.erp.minierp.modules.product.application.dto

import java.math.BigDecimal

data class ProductPurchaseItemDto(
    val id: String,
    val publicId: String,
    val name: String,
    val brand: String,
    val category: String,
    val vatRate: BigDecimal,
    val imageUrl: String? = null,
    val active: Boolean
)
