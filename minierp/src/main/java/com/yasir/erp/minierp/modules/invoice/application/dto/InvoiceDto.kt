package com.yasir.erp.minierp.modules.invoice.application.dto

import com.yasir.erp.minierp.modules.customer.application.dto.CustomerInvoiceDto
import com.yasir.erp.minierp.modules.order.application.dto.OrderInvoiceDto
import com.yasir.erp.minierp.modules.user.application.dto.UserInvoiceDto
import java.math.BigDecimal
import java.time.LocalDateTime

data class InvoiceDto(
    val id: String,
    val invoiceNumber: String,
    val user: UserInvoiceDto,
    val customer: CustomerInvoiceDto,
    val order: OrderInvoiceDto,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val invoiceDate: LocalDateTime,
    val totalAmount: BigDecimal,
    val taxAmount: BigDecimal,
    val finalAmount: BigDecimal,
    val issuedBy: String,
    val receivedBy: String,
    val active: Boolean
)
