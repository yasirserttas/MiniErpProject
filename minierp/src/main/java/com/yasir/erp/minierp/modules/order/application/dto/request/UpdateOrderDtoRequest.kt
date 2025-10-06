package com.yasir.erp.minierp.modules.order.application.dto.request

import com.yasir.erp.minierp.modules.order.domain.model.OrderStatus

data class UpdateOrderDtoRequest(
    val id: String,
    val orderStatus: OrderStatus,
    val shippingAddressId: String,
    val billingAddressId: String,
    val note: String? = null
)