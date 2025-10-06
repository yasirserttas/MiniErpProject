package com.yasir.erp.minierp.modules.customer.application.dto


import com.yasir.erp.minierp.modules.order.application.dto.OrderCustomerDto
import com.yasir.erp.minierp.modules.user.application.dto.UserCustomerDto
import com.yasir.erp.minierp.modules.address.application.dto.AddressCustomerDto
import java.time.LocalDateTime
import java.util.UUID

data class CustomerDto(
    val id: UUID,
    val name: String,
    val surName: String,
    val companyName: String,
    val phoneNumber: String,
    val email: String,
    val taxNumber: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val user: UserCustomerDto,
    val addresses: Set<AddressCustomerDto>?,
    val orders: Set<OrderCustomerDto>?,
    val active: Boolean
)
