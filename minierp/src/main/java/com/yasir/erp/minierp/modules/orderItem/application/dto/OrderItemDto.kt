package com.yasir.erp.minierp.modules.orderItem.application.dto

import com.yasir.erp.minierp.modules.order.application.dto.OrderOrderItemDto
import com.yasir.erp.minierp.modules.product.application.dto.ProductOrderItemDto
import java.math.BigDecimal

data class OrderItemDto(
    val id: String,
    val order: OrderOrderItemDto,
    val product: ProductOrderItemDto,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val totalPriceWithTax: BigDecimal
)