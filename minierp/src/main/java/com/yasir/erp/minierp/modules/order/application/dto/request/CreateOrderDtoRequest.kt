package com.yasir.erp.minierp.modules.order.application.dto.request

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerOrderDto
import com.yasir.erp.minierp.modules.user.application.dto.UserOrderDto
import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus
import com.yasir.erp.minierp.modules.orderItem.application.dto.request.CreateOrderItemDtoRequest
import java.math.BigDecimal
import java.time.LocalDateTime

data class CreateOrderDtoRequest(
    val user: UserOrderDto,
    val customer: CustomerOrderDto,
    val orderDate: LocalDateTime,
    val orderStatus: OrderStatus,
    val totalAmount: BigDecimal,
    val shippingAddressId: String,
    val billingAddressId: String,
    val note: String?=null,
    val orderItems: Set<CreateOrderItemDtoRequest>
)
