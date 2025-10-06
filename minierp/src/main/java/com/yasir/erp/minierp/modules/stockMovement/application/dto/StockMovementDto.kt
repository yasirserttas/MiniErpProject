package com.yasir.erp.minierp.modules.stockMovement.application.dto

import com.yasir.erp.minierp.modules.product.application.dto.ProductStockMovementDto
import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovementType
import java.time.LocalDateTime

data class StockMovementDto(
    val id: String,
    val product: ProductStockMovementDto,
    val quantity: Int,
    val movementType: StockMovementType,
    val description: String?,
    val date: LocalDateTime
)
