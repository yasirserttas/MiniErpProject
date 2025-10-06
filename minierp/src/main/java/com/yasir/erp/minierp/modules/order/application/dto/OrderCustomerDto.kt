package com.yasir.erp.minierp.modules.order.application.dto

import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderCustomerDto(
    val id: String,
    val orderStatus: OrderStatus,
    val orderDate: LocalDateTime,
    val totalAmount: BigDecimal,
    val active: Boolean
)
