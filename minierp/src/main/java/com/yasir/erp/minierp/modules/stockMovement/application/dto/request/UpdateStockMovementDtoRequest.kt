package com.yasir.erp.minierp.modules.stockMovement.application.dto.request

import com.yasir.erp.minierp.modules.stockMovement.domain.model.StockMovementType

data class UpdateStockMovementDtoRequest(
    val id: String,
    val quantity: Int,
    val movementType: StockMovementType,
    val description: String? = null
)
