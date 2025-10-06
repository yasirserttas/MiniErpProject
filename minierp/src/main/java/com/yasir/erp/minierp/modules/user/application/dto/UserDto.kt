package com.yasir.erp.minierp.modules.user.application.dto


import com.yasir.erp.minierp.modules.address.application.dto.AddressUserDto
import com.yasir.erp.minierp.modules.customer.application.dto.CustomerUserDto
import com.yasir.erp.minierp.modules.invoice.application.dto.InvoiceUserDto
import com.yasir.erp.minierp.modules.order.application.dto.OrderUserDto
import com.yasir.erp.minierp.modules.product.application.dto.ProductUserDto
import com.yasir.erp.minierp.modules.user.domain.model.Role
import java.time.LocalDateTime
import java.util.UUID

data class UserDto(
    val id: UUID,
    val name: String,
    val surName: String,
    val username: String,
    val companyName: String,
    val phoneNumber: String,
    val email: String,
    val taxNumber: String,
    val createdAt: LocalDateTime,
    val addresses: Set<AddressUserDto>?,
    val order  : Set<OrderUserDto>?,
    val products: Set<ProductUserDto> ?,
    val customers: Set<CustomerUserDto>?,
    val invoice: Set<InvoiceUserDto>?,
    val roles: Set<Role>,
    val active: Boolean
)
