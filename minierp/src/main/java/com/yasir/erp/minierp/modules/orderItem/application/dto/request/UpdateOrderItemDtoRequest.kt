package com.yasir.erp.minierp.modules.orderItem.application.dto.request

import java.math.BigDecimal

data class UpdateOrderItemDtoRequest(
    val id: String,
    val quantity: Int,
    val unitPrice: BigDecimal,
)
