package com.yasir.erp.minierp.modules.orderItem.application.dto.request

import java.math.BigDecimal

data class CreateOrderItemDtoRequest(
    val productId: String,
    val orderId: String,
    val quantity: Int,
    val unitPrice: BigDecimal,
)
