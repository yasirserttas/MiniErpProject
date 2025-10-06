package com.yasir.erp.minierp.modules.product.application.dto

data class ProductStockMovementDto(
    val id: String,
    val publicId: String,
    val name: String,
    val brand: String,
    val imageUrl: String?,
    val active: Boolean
)
