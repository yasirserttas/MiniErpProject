package com.yasir.erp.minierp.modules.customer.application.dto

import java.util.UUID

data class CustomerInvoiceDto(
    val id: UUID,
    val name: String,
    val surName: String,
    val companyName: String,
    val taxNumber: String,
    val email: String,
    val active: Boolean
)
