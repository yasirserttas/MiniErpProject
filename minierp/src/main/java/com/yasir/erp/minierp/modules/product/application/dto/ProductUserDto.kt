package com.yasir.erp.minierp.modules.product.application.dto

import java.math.BigDecimal

data class ProductUserDto(
    val id: String,
    val publicId: String,
    val name: String,
    val brand: String,
    val price: BigDecimal,
    val vatRate: BigDecimal,
    val active: Boolean
)
