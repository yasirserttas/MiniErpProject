package com.yasir.erp.minierp.modules.orderItem.application.dto

import com.yasir.erp.minierp.modules.product.application.dto.ProductOrderItemDto
import java.math.BigDecimal

data class OrderItemOrderDto(
    val id: String,
    val product: ProductOrderItemDto,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val taxRateApplied: BigDecimal,
    val taxAmount: BigDecimal,
    val totalPriceWithTax: BigDecimal
)
