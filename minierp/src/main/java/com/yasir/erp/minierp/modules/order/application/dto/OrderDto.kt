package com.yasir.erp.minierp.modules.order.application.dto

import com.yasir.erp.minierp.modules.address.application.dto.AddressDto
import com.yasir.erp.minierp.modules.user.application.dto.UserOrderDto
import com.yasir.erp.minierp.modules.customer.application.dto.CustomerOrderDto
import com.yasir.erp.minierp.modules.orderItem.application.dto.OrderItemOrderDto
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderDto(
    val id: String,
    val customer: CustomerOrderDto,
    val user: UserOrderDto,
    val orderItems: Set<OrderItemOrderDto>?,
    val orderStatus: OrderStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val shippingAddress: AddressDto,
    val billingAddress: AddressDto,
    val orderDate: LocalDateTime,
    val totalAmount: BigDecimal,
    val note: String?,
    val active: Boolean
)
