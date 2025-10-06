package com.yasir.erp.minierp.modules.product.application.dto

data class ProductStockDto(
    val id: String,
    val publicId: String,
    val name: String,
    val brand: String,
    val imageUrl: String? = null,
    val active: Boolean
)
