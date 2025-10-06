package com.yasir.erp.minierp.modules.stock.application.dto

import com.yasir.erp.minierp.modules.product.application.dto.ProductStockDto
import java.time.LocalDateTime

data class StockDto(
    val id: String,
    val product: ProductStockDto,
    val quantity: Int,
    val lastUpdated: LocalDateTime
)
