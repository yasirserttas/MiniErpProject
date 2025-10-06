package com.yasir.erp.minierp.modules.customer.application.dto.request

import java.util.UUID

data class CreateCustomerDtoRequest(
    val name: String,
    val surName: String,
    val companyName: String,
    val phoneNumber: String,
    val email: String,
    val taxNumber: String,
    val userId: UUID,
)
